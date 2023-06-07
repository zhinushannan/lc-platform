package io.github.zhinushannan.lcplatformback.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PathBindResp {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    private String name;

    private String prefix;

    private Integer sort;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableMetaInfoId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean deleted;

    private List<PathBindResp> children;

}
