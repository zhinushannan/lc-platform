package io.github.zhinushannan.lcplatformback.dto.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class PathBindReq {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    private String name;

    private String icon;

    private String prefix;

    private Integer sort;

    private Boolean enable;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableMetaInfoId;

}
