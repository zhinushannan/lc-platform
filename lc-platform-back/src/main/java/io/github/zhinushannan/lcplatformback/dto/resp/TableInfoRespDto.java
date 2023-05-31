package io.github.zhinushannan.lcplatformback.dto.resp;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TableInfoRespDto {

    private Long id;

    private Integer physicsTableSerial;

    private String logicTableName;

    private String businessTableName;

    private Boolean hasCreated;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
