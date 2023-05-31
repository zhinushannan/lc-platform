package io.github.zhinushannan.lcplatformback.dto.condition;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.bean.PageCondition;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TableInfoPageCondition extends PageCondition<TableMetaInfo> {

    private String logicName;

    private String businessName;

    @Override
    public QueryWrapper<TableMetaInfo> getQueryWrapper() {
        return super.getQueryWrapper()
                .like(StrUtil.isNotBlank(logicName), "logic_table_name", logicName)
                .like(StrUtil.isNotBlank(businessName), "business_table_name", businessName);
    }
}
