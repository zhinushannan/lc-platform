package io.github.zhinushannan.lcplatformback.system;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Cache {

    protected static Map<String, TableMetaInfo> TABLE_META_INFO_MAP = new HashMap<>();
    protected static Map<Long, List<FieldMetaInfo>> FIELD_META_INFO_MAP = new HashMap<>();

    protected static List<String> TABLE_LOGIC_NAME = new ArrayList<>();

    protected static List<String> TABLE_BUSINESS_NAME = new ArrayList<>();

    public static TableMetaInfo getTableMetaInfoByTableLogicName(String logicName) {
        return TABLE_META_INFO_MAP.get(logicName);
    }

    public static List<FieldMetaInfo> getFieldMetaInfosByTableId(Long tableId) {
        return FIELD_META_INFO_MAP.get(tableId);
    }

    public static Boolean hasTableLogicNameExist(String tableLogicName) {
        return TABLE_LOGIC_NAME.contains(tableLogicName);
    }

    public static Boolean hasTableBusinessNameExist(String tableBusinessName) {
        return TABLE_BUSINESS_NAME.contains(tableBusinessName);
    }

}
