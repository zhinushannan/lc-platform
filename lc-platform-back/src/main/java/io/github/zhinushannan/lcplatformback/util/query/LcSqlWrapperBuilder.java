package io.github.zhinushannan.lcplatformback.util.query;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.LcQueryException;
import io.github.zhinushannan.lcplatformback.system.Cache;

import java.util.List;

public class LcSqlWrapperBuilder {

    private StringBuilder sql = new StringBuilder();

    public LcSqlWrapperBuilder() {
    }

    public LcSqlWrapperBuilder sql(String sql) {
        this.sql.append(" ").append(sql).append(" ");
        return this;
    }

    public LcSqlWrapperBuilder field(String tableLogicName, String fieldLogicName) {
        List<FieldMetaInfo> fieldMetaInfos = Cache.getFieldMetaInfoByTableLogicName(tableLogicName);
        if (null == fieldMetaInfos) {
            throw LcQueryException.TABLE_NOT_FOUND;
        }
        for (FieldMetaInfo fieldMetaInfo : fieldMetaInfos) {
            if (fieldMetaInfo.getLogicFieldName().equals(fieldLogicName)) {
                this.sql.append(" ").append("f_").append(fieldMetaInfo.getPhysicsFieldSerial()).append(" ");
                return this;
            }
        }
        throw LcQueryException.FIELD_NOT_FOUND;
    }

    public LcSqlWrapperBuilder table(String tableLogicName) {
        TableMetaInfo tableMetaInfo = Cache.getTableMetaInfoByTableLogicName(tableLogicName);
        if (null == tableMetaInfo) {
            throw LcQueryException.TABLE_NOT_FOUND;
        }
        this.sql.append(" ").append("t_").append(tableMetaInfo.getPhysicsTableSerial()).append(" ");
        return this;
    }

    public String sql() {
        return this.sql.toString();
    }

}
