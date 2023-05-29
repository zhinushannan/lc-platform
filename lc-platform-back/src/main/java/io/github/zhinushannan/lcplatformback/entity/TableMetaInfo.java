package io.github.zhinushannan.lcplatformback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhinushannan.lcplatformback.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zhinushannan
 * @since 2023-05-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("table_meta_info")
public class TableMetaInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("physics_table_name")
    private String physicsTableName;

    @TableField("business_table_name")
    private String businessTableName;

    @TableField("show_table_name")
    private String showTableName;


}
