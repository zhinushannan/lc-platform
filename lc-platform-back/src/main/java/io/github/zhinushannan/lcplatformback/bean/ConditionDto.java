package io.github.zhinushannan.lcplatformback.bean;

import cn.hutool.json.JSONObject;
import lombok.*;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConditionDto extends PageCondition<Map<String, Object>> {

    private JSONObject conditions;

}
