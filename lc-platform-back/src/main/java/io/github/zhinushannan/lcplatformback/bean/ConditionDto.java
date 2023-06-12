package io.github.zhinushannan.lcplatformback.bean;

import cn.hutool.core.util.ObjectUtil;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConditionDto extends PageCondition<Map<String, Object>> {

    private List<Condition> conditions;

    public String sqlWhere(List<FieldMetaInfo> fieldMetaInfos) {
        StringBuilder sql = new StringBuilder();

        Map<String, FieldMetaInfo> collect = fieldMetaInfos.stream().collect(Collectors.toMap(FieldMetaInfo::getBusinessFieldName, f -> f));
        for (Condition condition : conditions) {
            FieldMetaInfo fieldMetaInfos1 = collect.get(condition.getBusinessFieldName());
            if (fieldMetaInfos1 != null && ObjectUtil.isNotEmpty(condition.keyword)) {
                sql.append(" and `f_").append(fieldMetaInfos1.getPhysicsFieldSerial()).append("` ");
                if (condition.getSearchMode() == 1 || condition.getSearchMode() == 3) {
                    sql.append(" = ").append(condition.getKeyword());
                } else if (condition.getSearchMode() == 2) {
                    sql.append(" like ").append("concat('%', ").append(condition.getKeyword()).append(", '%') ");
                }
            }
        }
        return sql.toString();
    }

    @Data
    public static class Condition {
        private String businessFieldName;
        private Object keyword;
        private Integer searchMode;
    }

}
