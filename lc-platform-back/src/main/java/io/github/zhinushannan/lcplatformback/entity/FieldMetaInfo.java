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
@TableName("field_meta_info")
public class FieldMetaInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("physics_field_name")
    private String physicsFieldName;

    @TableField("field_type")
    private Integer fieldType;

    @TableField("field_length")
    private Integer fieldLength;

    @TableField("nullable")
    private Boolean nullable;

    @TableField("business_field_name")
    private String businessFieldName;

    @TableField("show_field_name")
    private String showFieldName;

    @TableField("table_metainfo_id")
    private Long tableMetainfoId;


}
