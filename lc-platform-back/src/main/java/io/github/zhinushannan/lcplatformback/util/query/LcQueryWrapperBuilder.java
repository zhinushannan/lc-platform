package io.github.zhinushannan.lcplatformback.util.query;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.LcQueryException;
import io.github.zhinushannan.lcplatformback.exception.TableMetaInfoException;
import io.github.zhinushannan.lcplatformback.system.Cache;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 生成查询语句的建造者
 * 功能包含：
 * 1、等于 eq
 * 2、不等于 ne
 * 3、小于 lt
 * 4、小于等于 lte
 * 5、大于 gt
 * 6、大于等于 gte
 * 7、包含 in
 * 8、不包含 notIn
 * 9、模糊 like
 * 10、左模糊 lLike
 * 11、右模糊 rLike
 * 12、是空 isNull
 * 13、不是空 notNull
 * 14、排序
 */
public class LcQueryWrapperBuilder {

    private List<Map<String, Object>> conditions;

    private Map<String, String> orderBy;

    private Map<String, FieldMetaInfo> fieldMetaInfoMap;
    private final String tableLogicName;

    private void init() {
        conditions = new ArrayList<>();
        conditions.add(new HashMap<>());

        orderBy = new LinkedHashMap<>();

        fieldMetaInfoMap = Cache.getFieldMetaInfoByTableLogicName(tableLogicName).stream().collect(Collectors.toMap(FieldMetaInfo::getBusinessFieldName, f -> f));
    }

    public LcQueryWrapperBuilder(String tableLogicName) {
        this.tableLogicName = tableLogicName;
        TableMetaInfo tableMetaInfo = Cache.getTableMetaInfoByTableLogicName(tableLogicName);
        if (tableMetaInfo == null) {
            throw TableMetaInfoException.NOW_FOUND;
        }
        this.init();
    }

    public LcQueryWrapper build() {
        return new LcQueryWrapper(conditions, orderBy, tableLogicName);
    }

    public void clear() {
        this.init();
    }

    public LcQueryWrapperBuilder eq(String businessField, Object val) {
        return this.eq(true, businessField, val);
    }

    public LcQueryWrapperBuilder eq(boolean condition, String businessField, Object val) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("eq(" + businessField + ")", val);
        }
        return this;
    }

    public LcQueryWrapperBuilder ne(String businessField, Object val) {
        return this.ne(true, businessField, val);
    }

    public LcQueryWrapperBuilder ne(boolean condition, String businessField, Object val) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("ne(" + businessField + ")", val);
        }
        return this;
    }

    public LcQueryWrapperBuilder lt(String businessField, Object val) {
        return this.lt(true, businessField, val);
    }

    public LcQueryWrapperBuilder lt(boolean condition, String businessField, Object val) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("lt(" + businessField + ")", val);
        }
        return this;
    }

    public LcQueryWrapperBuilder lte(String businessField, Object val) {
        return this.lte(true, businessField, val);
    }

    public LcQueryWrapperBuilder lte(boolean condition, String businessField, Object val) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("lte(" + businessField + ")", val);
        }
        return this;
    }

    public LcQueryWrapperBuilder gt(String businessField, Object val) {
        return this.gt(true, businessField, val);
    }

    public LcQueryWrapperBuilder gt(boolean condition, String businessField, Object val) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("gt(" + businessField + ")", val);
        }
        return this;
    }

    public LcQueryWrapperBuilder gte(String businessField, Object val) {
        return this.gte(true, businessField, val);
    }

    public LcQueryWrapperBuilder gte(boolean condition, String businessField, Object val) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("gte(" + businessField + ")", val);
        }
        return this;
    }

    public LcQueryWrapperBuilder in(String businessField, Collection<Object> value) {
        return this.in(true, businessField, value);
    }

    public LcQueryWrapperBuilder in(boolean condition, String businessField, Collection<Object> value) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("in(" + businessField + ")", value);
        }
        return this;
    }

    public LcQueryWrapperBuilder notIn(String businessField, Collection<Object> value) {
        return this.notIn(true, businessField, value);
    }

    public LcQueryWrapperBuilder notIn(boolean condition, String businessField, Collection<Object> value) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("not_in(" + businessField + ")", value);
        }
        return this;
    }

    public LcQueryWrapperBuilder like(String businessField, Collection<Object> value) {
        return like(true, businessField, value);
    }

    public LcQueryWrapperBuilder like(boolean condition, String businessField, Collection<Object> value) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("like(" + businessField + ")", "%" + value + "%");
        }
        return this;
    }

    public LcQueryWrapperBuilder lLike(String businessField, Collection<Object> value) {
        return lLike(true, businessField, value);
    }

    public LcQueryWrapperBuilder lLike(boolean condition, String businessField, Collection<Object> value) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("like(" + businessField + ")", "%" + value);
        }
        return this;
    }

    public LcQueryWrapperBuilder rLike(String businessField, Collection<Object> value) {
        return rLike(true, businessField, value);
    }

    public LcQueryWrapperBuilder rLike(boolean condition, String businessField, Collection<Object> value) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("like(" + businessField + ")", value + "%");
        }
        return this;
    }

    public LcQueryWrapperBuilder isNull(String businessField) {
        return isNull(true, businessField);
    }

    public LcQueryWrapperBuilder isNull(boolean condition, String businessField) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("is_null(" + businessField + ")", "null");
        }
        return this;
    }

    public LcQueryWrapperBuilder notNull(String businessField) {
        return notNull(true, businessField);
    }

    public LcQueryWrapperBuilder notNull(boolean condition, String businessField) {
        checkBusinessName(businessField);
        if (condition) {
            this.conditions.get(this.conditions.size() - 1).put("not_null(" + businessField + ")", "null");
        }
        return this;
    }

    public LcQueryWrapperBuilder orderByAsc(String businessField) {
        return this.orderByAsc(true, businessField);
    }

    public LcQueryWrapperBuilder orderByAsc(boolean condition, String businessField) {
        checkBusinessName(businessField);
        if (condition) {
            this.orderBy.put(businessField, "asc");
        }
        return this;
    }

    public LcQueryWrapperBuilder orderByDesc(String businessField) {
        return this.orderByDesc(true, businessField);
    }

    public LcQueryWrapperBuilder orderByDesc(boolean condition, String businessField) {
        checkBusinessName(businessField);
        if (condition) {
            this.orderBy.put(businessField, "desc");
        }
        return this;
    }

    public LcQueryWrapperBuilder or() {
        this.conditions.add(new HashMap<>());
        return this;
    }

    private void checkBusinessName(String businessField) {
        Optional.ofNullable(fieldMetaInfoMap.get(businessField)).orElseThrow(() -> new LcQueryException(businessField + "不存在！"));
    }

}
