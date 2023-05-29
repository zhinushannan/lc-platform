package io.github.zhinushannan.lcplatformback.service.impl;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.mapper.CreateBusinessMapper;
import io.github.zhinushannan.lcplatformback.service.CreateBusinessService;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
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
    @Override
    public Boolean createBusinessService(TableMetaInfo tableMetaInfo, List<FieldMetaInfo> fieldMetaInfos) {
        // todo 需要对两个实体类进行合法性校验
        tableMetaInfoService.save(tableMetaInfo);
        fieldMetaInfoService.saveBatch(fieldMetaInfos);

        String tableName = "";

        StringBuilder ddl = new StringBuilder();
        List<String> fieldDdl = new ArrayList<>();

        for (FieldMetaInfo fieldMetaInfo : fieldMetaInfos) {
            ddl.delete(0, ddl.length());
            ddl.append("`").append(fieldMetaInfo.getPhysicsFieldName()).append("`").append(" ")
                    .append(fieldMetaInfo.getFieldType());
            // todo 判断是否需要长度 和 默认长度
            boolean needLength = true;
            if (needLength) {
                ddl.append("(").append(fieldMetaInfo.getFieldLength()).append(")");
            }
            ddl.append(" ");
            if (!fieldMetaInfo.getNullable()) {
                ddl.append("NOT NULL");
            }
            ddl.append(",");
            fieldDdl.add(ddl.toString());
        }

        int line = createBusinessMapper.createNewTable(tableName, fieldDdl);

        return null;
    }
}
