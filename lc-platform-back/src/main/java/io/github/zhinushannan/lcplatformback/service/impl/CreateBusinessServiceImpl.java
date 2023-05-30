package io.github.zhinushannan.lcplatformback.service.impl;

import io.github.zhinushannan.lcplatformback.constant.MySQLTypeEnum;
import io.github.zhinushannan.lcplatformback.dto.req.CreateBusinessDto;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.mapper.CreateBusinessMapper;
import io.github.zhinushannan.lcplatformback.service.CreateBusinessService;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import io.github.zhinushannan.lcplatformback.system.SystemConstant;
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

    // todo 需要进行事务控制
    //  1、需要对逻辑表明做唯一处理
    @Override
    public Boolean createBusinessService(CreateBusinessDto dto) {
        // 组装 TableMetaInfo
        CreateBusinessDto.TableInfo tableInfo = dto.getTableInfo();

        TableMetaInfo tableMetaInfo = TableMetaInfo.builder()
                .physicsTableSerial(SystemConstant.getNextLogicTableNameSerial())
                .logicTableName(tableInfo.getTableLogicName())
                .businessTableName(tableInfo.getTableBusinessName())
                .build();
        tableMetaInfoService.save(tableMetaInfo);

        // 组装 FieldInfo 列表
        List<CreateBusinessDto.FieldInfo> fieldInfos = dto.getFieldInfos();

        int fieldSerial = 0;
        List<FieldMetaInfo> fieldMetaInfos = new ArrayList<>();
        for (CreateBusinessDto.FieldInfo fieldInfo : fieldInfos) {
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

        // todo CreateBusinessDto.PageInfo pageInfo = dto.getPageInfo();

        return null;
    }
}
