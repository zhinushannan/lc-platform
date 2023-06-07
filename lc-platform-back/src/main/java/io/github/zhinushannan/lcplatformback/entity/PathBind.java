package io.github.zhinushannan.lcplatformback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.zhinushannan.lcplatformback.bean.BaseEntity;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author zhinushannan
 * @since 2023-06-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("path_bind")
public class PathBind extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("parent_id")
    private Long parentId;

    @TableField("name")
    private String name;

    @TableField("prefix")
    private String prefix;


    @TableField("table_meta_info_id")
    private Long tableMetaInfoId;

}