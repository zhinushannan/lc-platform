package io.github.zhinushannan.lcplatformback.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TableMetaInfoPathBindResp {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer physicsTableSerial;

    private String logicTableName;

    private String businessTableName;

    private PathBindResp pathBindResp;

}
