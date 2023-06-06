package io.github.zhinushannan.lcplatformback.dto.req;

import lombok.Data;

@Data
public class PathBindReq {

    private Long id;

    private Long parentId;

    private String name;

    private String prefix;

    private Long tableMetaInfoId;

}
