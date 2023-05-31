package io.github.zhinushannan.lcplatformback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.constant.MySQLTypeEnum;
import io.github.zhinushannan.lcplatformback.dto.req.TableInfoReq;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.TableMetaInfoException;
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
import java.util.List;

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
                throw TableMetaInfoException.LOGIC_OR_BUSINESS_REPEAT;
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
}
