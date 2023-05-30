package io.github.zhinushannan.lcplatformback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.zhinushannan.lcplatformback.bean.BaseEntity;
import lombok.*;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("table_meta_info")
public class TableMetaInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("physics_table_serial")
    private Integer physicsTableSerial;

    @TableField("logic_table_name")
    private String logicTableName;

    @TableField("business_table_name")
    private String businessTableName;

}
