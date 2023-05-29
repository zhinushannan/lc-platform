package io.github.zhinushannan.lcplatformback.bean;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页条件查询。是所有条件查询的父类
 *
 * @param <T>
 *         条件查询的实体类
 *
 * @author Zhinushannan
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageCondition<T> {

    /**
     * 当前页码
     */
    @ApiModelProperty("页码")
    private Long current = 1L;

    /**
     * 页面大小
     */
    @ApiModelProperty("页面大小")
    private Long size = 10L;

    /**
     * @return 返回 Page 对象
     */
    public Page<T> getPage() {
        return Page.of(current, size);
    }

    /**
     * @return 返回根据条件构造的 QueryWrapper
     */
    public QueryWrapper<T> getQueryWrapper() {
        return new QueryWrapper<>();
    }

}
