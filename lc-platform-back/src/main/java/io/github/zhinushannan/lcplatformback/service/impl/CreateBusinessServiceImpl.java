package io.github.zhinushannan.lcplatformback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.bean.BaseEntity;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.constant.MySQLTypeEnum;
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
        synchronized (this) {
            systemInitialization.refreshCache();

            // 组装 TableMetaInfo
            TableInfoReq.TableInfo tableInfo = req.getTableInfo();

            TableMetaInfo tableMetaInfo = TableMetaInfo.builder()
                    .physicsTableSerial(SystemConstant.getNextLogicTableNameSerial())
                    .logicTableName(tableInfo.getTableLogicName())
                    .businessTableName(tableInfo.getTableBusinessName())
                    .hasCreated(false)
                    .build();

            Integer logicCode = tableMetaInfoService.checkTableLogic(tableMetaInfo.getLogicTableName()).getCode();
            Integer businessCode = tableMetaInfoService.checkTableBusiness(tableInfo.getTableBusinessName()).getCode();
            if (logicCode == 500 || businessCode == 500) {
                throw CreateBusinessException.TABLE_LOGIC_OR_BUSINESS_REPEAT;
            }

            tableMetaInfoService.save(tableMetaInfo);

            // 组装 FieldInfo 列表
            List<TableInfoReq.FieldInfo> fieldInfos = req.getFieldInfos();

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
                        .build();
                fieldMetaInfos.add(fieldMetaInfo);
            }

            fieldMetaInfoService.saveBatch(fieldMetaInfos);

            systemInitialization.refreshCache();
            return ResultBean.success();
        }
    }

    // todo 需要进行事务控制
    //  1、需要对逻辑表明做唯一处理
    @Override
    public Boolean createBusinessService(TableInfoReq dto) {



//
//        // 组装 tablename、字段的 ddl 语句
//        String tableName = DBConvert.tableNameConvertBySerial(tableMetaInfo.getPhysicsTableSerial());
//
//        StringBuilder ddl = new StringBuilder();
//        List<String> fieldDdl = new ArrayList<>();
//
//        for (FieldMetaInfo fieldMetaInfo : fieldMetaInfos) {
//            ddl.delete(0, ddl.length());
//            ddl.append("`").append(DBConvert.fieldNameConvertBySerial(fieldMetaInfo.getPhysicsFieldSerial())).append("`").append(" ")
//                    .append(fieldMetaInfo.getFieldType());
//
//            boolean needLength = fieldMetaInfo.getFieldLength() != null;
//            if (needLength) {
//                ddl.append("(").append(fieldMetaInfo.getFieldLength()).append(")");
//            }
//            ddl.append(" ");
//            if (!fieldMetaInfo.getNullable()) {
//                ddl.append("NOT NULL");
//            }
//            ddl.append(", ");
//            fieldDdl.add(ddl.toString());
//        }
//
//        int line = createBusinessMapper.createNewTable(tableName, fieldDdl);

        // todo CreateBusinessDto.PageInfo pageInfo = dto.getPageInfo();

        return null;
    }

    @Override
    public ResultBean<String> selectShowFields(SelectEnableFieldsReq req) {
        List<FieldMetaInfo> fieldMetaInfos = checkEnable(req);
        List<Long> fieldIds = req.getFieldIds();

        List<FieldMetaInfo> result = fieldMetaInfos.stream()
                .filter(fieldMetaInfo -> fieldIds.contains(fieldMetaInfo.getId()))
                .peek(fieldMetaInfo -> fieldMetaInfo.setEnableShow(true))
                .collect(Collectors.toList());
        fieldMetaInfoService.updateBatchById(result);

        return ResultBean.success();
    }

    @Override
    public ResultBean<String> selectSearchFields(SelectEnableFieldsReq req) {
        List<FieldMetaInfo> fieldMetaInfos = checkEnable(req);
        List<Long> fieldIds = req.getFieldIds();

        List<FieldMetaInfo> result = fieldMetaInfos.stream()
                .filter(fieldMetaInfo -> fieldIds.contains(fieldMetaInfo.getId()))
                .peek(fieldMetaInfo -> fieldMetaInfo.setEnableSearch(true))
                .collect(Collectors.toList());
        fieldMetaInfoService.updateBatchById(result);

        return ResultBean.success();
    }

    /**
     * 校验开启展示和搜索的合法性
     */
    private List<FieldMetaInfo> checkEnable(SelectEnableFieldsReq req) {
        Long tableId = req.getTableId();
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getById(tableId);
        if (tableMetaInfo == null) {
            throw CreateBusinessException.TABLE_NOTFOUND;
        }
        List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list(new QueryWrapper<FieldMetaInfo>().in("table_meta_info_id"));

        List<Long> fieldIdsDb = fieldMetaInfos.stream().map(BaseEntity::getId).collect(Collectors.toList());

        List<Long> fieldIds = req.getFieldIds();
        if (new HashSet<>(fieldIdsDb).containsAll(fieldIds)) {
            throw CreateBusinessException.FIELD_ERROR;
        }

        return fieldMetaInfos;
    }

}
