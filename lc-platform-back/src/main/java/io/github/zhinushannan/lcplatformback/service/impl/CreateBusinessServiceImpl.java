package io.github.zhinushannan.lcplatformback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.constant.MySQLTypeEnum;
import io.github.zhinushannan.lcplatformback.constant.SelectModeEnum;
import io.github.zhinushannan.lcplatformback.dto.req.SelectEnableFieldsReq;
import io.github.zhinushannan.lcplatformback.dto.req.TableInfoReq;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.CreateBusinessException;
import io.github.zhinushannan.lcplatformback.mapper.CreateBusinessMapper;
import io.github.zhinushannan.lcplatformback.service.CreateBusinessService;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import io.github.zhinushannan.lcplatformback.system.SystemConstant;
import io.github.zhinushannan.lcplatformback.system.SystemInitialization;
import io.github.zhinushannan.lcplatformback.util.DBConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateBusinessServiceImpl implements CreateBusinessService {

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @Autowired
    private FieldMetaInfoService fieldMetaInfoService;

    @Autowired
    private CreateBusinessMapper createBusinessMapper;

    @Autowired
    private SystemInitialization systemInitialization;

    @Override
    public ResultBean<String> saveTableInfo(TableInfoReq req) {
        systemInitialization.refreshCache();

        // 1 合法性校验

        // 1.1 表逻辑名 和 表业务名
        Integer logicCode = tableMetaInfoService.checkTableLogic(req.getTableInfo().getTableLogicName()).getCode();
        Integer businessCode = tableMetaInfoService.checkTableBusiness(req.getTableInfo().getTableBusinessName()).getCode();
        if (logicCode == 500 || businessCode == 500) {
            throw CreateBusinessException.TABLE_LOGIC_OR_BUSINESS_REPEAT;
        }

        // 1.2 字段逻辑名 和 字段业务名
        List<TableInfoReq.FieldInfo> fieldInfos = req.getFieldInfos();
        if (fieldInfos == null || fieldInfos.isEmpty()) {
            throw CreateBusinessException.FIELD_NULL;
        }
        List<String> fieldBusinessName = fieldInfos.stream().map(TableInfoReq.FieldInfo::getFieldBusinessName).collect(Collectors.toList());
        if (fieldBusinessName.size() != new HashSet<>(fieldBusinessName).size()) {
            throw CreateBusinessException.FIELD_BUSINESS_REPEAT;
        }

        List<String> fieldLogicName = fieldInfos.stream().map(TableInfoReq.FieldInfo::getFieldLogicName).collect(Collectors.toList());
        if (fieldLogicName.size() != new HashSet<>(fieldLogicName).size()) {
            throw CreateBusinessException.FIELD_LOGIC_REPEAT;
        }

        // 组装 TableMetaInfo
        TableInfoReq.TableInfo tableInfo = req.getTableInfo();

        TableMetaInfo tableMetaInfo = TableMetaInfo.builder()
                .physicsTableSerial(SystemConstant.getNextLogicTableNameSerial())
                .logicTableName(tableInfo.getTableLogicName())
                .businessTableName(tableInfo.getTableBusinessName())
                .build();

        tableMetaInfoService.save(tableMetaInfo);

        // 组装 FieldInfo 列表
        int fieldSerial = 0;
        List<FieldMetaInfo> fieldMetaInfos = new ArrayList<>();
        for (TableInfoReq.FieldInfo fieldInfo : fieldInfos) {
            MySQLTypeEnum mysqlType = MySQLTypeEnum.getByJdbcType(fieldInfo.getFieldJdbcType());

            FieldMetaInfo fieldMetaInfo = FieldMetaInfo.builder()
                    .physicsFieldSerial(fieldSerial++)
                    .logicFieldName(fieldInfo.getFieldLogicName())
                    .businessFieldName(fieldInfo.getFieldBusinessName())
                    .tableMetaInfoId(tableMetaInfo.getId())
                    .fieldType(mysqlType.getJdbcType())
                    .fieldLength(
                            mysqlType.getNeedLength() ?
                                    fieldInfo.getFieldJdbcLength() != null && fieldInfo.getFieldJdbcLength() > 0 ? fieldInfo.getFieldJdbcLength() : mysqlType.getDefaultLength() :
                                    null
                    )
                    .nullable(fieldInfo.getNullable())
                    .enableShow(fieldInfo.getEnableShow())
                    .searchMode(SelectModeEnum.DISABLE.getValue())
                    .build();
            fieldMetaInfos.add(fieldMetaInfo);
        }

        fieldMetaInfoService.saveBatch(fieldMetaInfos);


        this.createPhysicsTable(tableMetaInfo.getId());

        systemInitialization.refreshCache();

        return ResultBean.success(tableMetaInfo.getId().toString());
    }

    @Override
    public ResultBean<String> selectShowFields(SelectEnableFieldsReq req) {
        FieldMetaInfo fieldMetaInfo = checkEnable(req);
        fieldMetaInfo.setEnableShow(req.getShowEnable());
        fieldMetaInfoService.updateById(fieldMetaInfo);
        return ResultBean.success();
    }

    @Override
    public ResultBean<String> selectSearchFields(SelectEnableFieldsReq req) {
        FieldMetaInfo fieldMetaInfo = checkEnable(req);
        fieldMetaInfo.setSearchMode(SelectModeEnum.getLabelByValue(req.getSelectMode()).getValue());
        fieldMetaInfoService.updateById(fieldMetaInfo);
        return ResultBean.success();
    }

    private ResultBean<String> createPhysicsTable(Long tableId) {

        synchronized (this) {
            systemInitialization.refreshCache();

            TableMetaInfo tableMetaInfo = tableMetaInfoService.getById(tableId);
            if (tableMetaInfo == null) {
                return ResultBean.notFound("表不存在！");
            }

            List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list(new QueryWrapper<FieldMetaInfo>().eq("table_meta_info_id", tableId));

            // 组装 tablename、字段的 ddl 语句
            String tableName = DBConvert.tableNameConvertBySerial(tableMetaInfo.getPhysicsTableSerial());

            StringBuilder ddl = new StringBuilder();
            List<String> fieldDdl = new ArrayList<>();

            for (FieldMetaInfo fieldMetaInfo : fieldMetaInfos) {
                ddl.delete(0, ddl.length());
                ddl.append("`").append(DBConvert.fieldNameConvertBySerial(fieldMetaInfo.getPhysicsFieldSerial())).append("`").append(" ")
                        .append(fieldMetaInfo.getFieldType());

                boolean needLength = fieldMetaInfo.getFieldLength() != null;
                if (needLength) {
                    ddl.append("(").append(fieldMetaInfo.getFieldLength()).append(")");
                }
                ddl.append(" ");
                if (!fieldMetaInfo.getNullable()) {
                    ddl.append("NOT NULL");
                }
                ddl.append(", ");
                fieldDdl.add(ddl.toString());
            }

            int line = createBusinessMapper.createNewTable(tableName, fieldDdl);

            systemInitialization.refreshCache();
            return ResultBean.success();
        }

    }

    /**
     * 校验开启展示和搜索的合法性
     */
    private FieldMetaInfo checkEnable(SelectEnableFieldsReq req) {
        Long tableId = req.getTableId();
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getById(tableId);
        if (tableMetaInfo == null) {
            throw CreateBusinessException.TABLE_NOTFOUND;
        }

        FieldMetaInfo fieldMetaInfo = fieldMetaInfoService.getById(req.getFieldId());

        if (null == fieldMetaInfo) {
            throw CreateBusinessException.FIELD_ERROR;
        }

        return fieldMetaInfo;
    }

}
