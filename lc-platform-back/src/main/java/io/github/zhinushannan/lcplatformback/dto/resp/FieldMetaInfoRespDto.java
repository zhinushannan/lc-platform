package io.github.zhinushannan.lcplatformback.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class FieldMetaInfoRespDto {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableMetaInfoId;

    private String logicFieldName;

    private String businessFieldName;

    private String fieldType;

    private String fieldLength;

    private Boolean nullable;

    private Boolean enableShow;

    private Integer searchMode;

}
