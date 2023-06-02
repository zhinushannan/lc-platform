package io.github.zhinushannan.lcplatformback.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBTypeEnumResp {

    private String jdbcType;

    private String javaType;

    private Boolean needLength;

    private Integer defaultLength;

}
