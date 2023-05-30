package io.github.zhinushannan.lcplatformback.bean;

import cn.hutool.json.JSONObject;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConditionDto extends PageCondition<Object> {

    private JSONObject conditions;

}
