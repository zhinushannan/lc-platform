package io.github.zhinushannan.lcplatformback.system;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Cache {

    protected static Map<String, TableMetaInfo> TABLE_META_INFO_MAP = new HashMap<>();
    protected static Map<Long, List<FieldMetaInfo>> FIELD_META_INFO_MAP = new HashMap<>();

    public static TableMetaInfo getTableMetaInfoByTableLogicName(String logicName) {
        return TABLE_META_INFO_MAP.get(logicName);
    }

    public static List<FieldMetaInfo> getFieldMetaInfosByTableId(Long tableId) {
        return FIELD_META_INFO_MAP.get(tableId);
    }

}
