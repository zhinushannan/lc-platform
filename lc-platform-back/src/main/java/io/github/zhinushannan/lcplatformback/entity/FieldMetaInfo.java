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
@TableName("field_meta_info")
public class FieldMetaInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("physics_field_serial")
    private Integer physicsFieldSerial;

    @TableField("logic_field_name")
    private String logicFieldName;

    @TableField("business_field_name")
    private String businessFieldName;

    @TableField("table_meta_info_id")
    private Long tableMetaInfoId;

    @TableField("field_type")
    private String fieldType;

    @TableField("field_length")
    private Integer fieldLength;

    @TableField("nullable")
    private Boolean nullable;

    @TableField("enable_show")
    private Boolean enableShow;

    @TableField("search_mode")
    private Integer searchMode;

}
