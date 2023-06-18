package io.github.zhinushannan.lcplatformback.util.query;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.system.Cache;

import java.util.*;
import java.util.stream.Collectors;

public class LcQueryWrapper {

    private List<Map<String, Object>> conditions;

    private Map<String, String> orderBy;

    private StringBuilder sqlWhere = new StringBuilder();

    private StringBuilder sqlLimit = new StringBuilder();

    private StringBuilder sqlSelect = new StringBuilder();

    private StringBuilder sqlOrder = new StringBuilder();

    private StringBuilder sqlCount = new StringBuilder();

    private String tableLogicName;

    private TableMetaInfo tableMetaInfo;

    private List<FieldMetaInfo> fieldMetaInfos;

    private Page page;

    private Map<String, FieldMetaInfo> businessNameFields = new HashMap<>();

    private LcQueryWrapper() {
    }

    protected LcQueryWrapper(List<Map<String, Object>> conditions, Map<String, String> orderBy, String tableLogicName) {
        conditions.removeIf(Map::isEmpty);
        this.conditions = conditions;
        this.orderBy = orderBy;
        this.tableLogicName = tableLogicName;
        this.init();
    }

    private void init() {
        this.tableMetaInfo = Cache.getTableMetaInfoByTableLogicName(tableLogicName);
        this.fieldMetaInfos = Cache.getFieldMetaInfoByTableLogicName(tableLogicName);
        this.businessNameFields = this.fieldMetaInfos.stream().collect(Collectors.toMap(FieldMetaInfo::getBusinessFieldName, f -> f));

        this.sqlSelect();
        this.sqlWhere();
        this.sqlOrder();
        this.sqlLimit();
        this.sqlCount();
    }

    private void sqlSelect() {
        sqlSelect.append("select ");
        for (FieldMetaInfo fieldMetaInfo : this.fieldMetaInfos) {
            sqlSelect
                    .append(" `id` as id, `create_time` as createTime, `update_time` as updateTime, ")
                    .append("`f_").append(fieldMetaInfo.getPhysicsFieldSerial()).append("` ")
                    .append(" as ").append(fieldMetaInfo.getBusinessFieldName()).append(" ")
                    .append(" from ")
                    .append("`t_").append(this.tableMetaInfo.getPhysicsTableSerial()).append("`");
        }
    }

    private void sqlWhere() {
        sqlWhere.append(" where `deleted`=0 ");

        // ======== 组装 where 语句
        List<List<String>> sqlWheres = new ArrayList<>();

        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < conditions.size(); i++) {
            sqlWheres.add(new ArrayList<>());
            Map<String, Object> condition = conditions.get(i);
            for (Map.Entry<String, Object> kv : condition.entrySet()) {
                tmp.setLength(0);
                String key = kv.getKey();
                Object value = kv.getValue();

                String operateKeyword = key.split("\\(")[0].toLowerCase();
                String businessName = key.split("\\(")[1].split("\\)")[0];

                String phyField = "f_" + businessNameFields.get(businessName).getPhysicsFieldSerial();

                tmp.append(" ").append("`").append(phyField).append("`");
                switch (operateKeyword) {
                    case "eq":
                        tmp.append("=");
                        break;
                    case "ne":
                        tmp.append("<>");
                        break;
                    case "lt":
                        tmp.append("<");
                        break;
                    case "lte":
                        tmp.append("<=");
                        break;
                    case "gt":
                        tmp.append(">");
                        break;
                    case "gte":
                        tmp.append(">=");
                        break;
                    case "in":
                        tmp.append(" in ");
                        Collection<Object> vals1 = (Collection<Object>) value;
                        StringBuilder valStr1 = new StringBuilder();
                        valStr1.append(" ( ");
                        for (Object val : vals1) {
                            valStr1.append(val).append(", ");
                        }
                        valStr1.delete(valStr1.length() - 2, valStr1.length() - 1);
                        valStr1.append(" ) ");
                        value = valStr1.toString();
                        break;
                    case "not_in":
                        tmp.append(" not in ");
                        Collection<Object> vals2 = (Collection<Object>) value;
                        StringBuilder valStr2 = new StringBuilder();
                        valStr2.append(" ( ");
                        for (Object val : vals2) {
                            valStr2.append(val).append(", ");
                        }
                        valStr2.delete(valStr2.length() - 2, valStr2.length() - 1);
                        valStr2.append(" ) ");
                        value = valStr2.toString();
                        break;
                    case "like":
                    case "llkie":
                    case "rlike":
                        tmp.append(" like ");
                        break;
                    case "is_null":
                        tmp.append(" is ");
                        break;
                    case "not_null":
                        tmp.append(" is not ");
                        break;
                }
                tmp.append(value);
                sqlWheres.get(i).add(tmp.toString());
            }
        }
        for (int i = 0; i < sqlWheres.size(); i++) {
            if (i == 0) {
                this.sqlWhere.append(" and ");
            } else {
                this.sqlWhere.append(" or ");
            }
            List<String> sqlWhere = sqlWheres.get(i);
            this.sqlWhere.append(" ( ");
            for (int j = 0; j < sqlWhere.size(); j++) {
                if (j != 0) {
                    this.sqlWhere.append(" and ");
                }
                this.sqlWhere.append(sqlWhere.get(j));
            }
            this.sqlWhere.append(" ) ");
        }


    }

    private void sqlOrder() {
        // 组装 order 语句
        if (!orderBy.isEmpty()) {
            sqlOrder.append(" order by ");
            boolean isStart = true;
            for (Map.Entry<String, String> map : orderBy.entrySet()) {
                if (!isStart) {
                    sqlOrder.append(", ");
                } else {
                    isStart = false;
                }
                sqlOrder.append(" ").append(map.getKey()).append(" ").append(map.getValue()).append(" ");
            }
        }
    }

    private void sqlLimit() {
        if (page == null) {
            return;
        }
        sqlLimit.append(" ")
                .append("limit").append(" ")
                .append((page.getCurrent() - 1) * page.getSize())
                .append(", ")
                .append(page.getSize())
                .append(" ");
    }

    public void sqlCount() {
        sqlCount.append("select count(*) as total from ")
                .append(" `t_").append(this.tableMetaInfo.getPhysicsTableSerial()).append("` ")
                .append(this.sqlWhere);
    }

    public void setPage(Page page) {
        this.page = page;
        this.sqlLimit();
    }

    public String getSqlCount() {
        return sqlCount.toString();
    }

    public String getSql() {
        return sqlSelect + " " + sqlWhere + " " + sqlOrder + " " + sqlLimit;
    }

}
