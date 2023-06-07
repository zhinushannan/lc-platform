package io.github.zhinushannan.lcplatformback.dto.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.bean.PageCondition;
import io.github.zhinushannan.lcplatformback.entity.PathBind;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PathBindPageCondition extends PageCondition<PathBind> {

    @Override
    public QueryWrapper<PathBind> getQueryWrapper() {
        return super.getQueryWrapper().orderByAsc("sort");
    }
}
