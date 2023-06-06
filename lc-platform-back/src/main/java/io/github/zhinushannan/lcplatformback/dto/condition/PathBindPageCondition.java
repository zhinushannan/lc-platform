package io.github.zhinushannan.lcplatformback.dto.condition;

import io.github.zhinushannan.lcplatformback.bean.PageCondition;
import io.github.zhinushannan.lcplatformback.entity.PathBind;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PathBindPageCondition extends PageCondition<PathBind> {
}
