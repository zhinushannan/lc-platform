package io.github.zhinushannan.lcplatformback.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class SelectEnableFieldsReq {

    private Long tableId;

    private List<Long> fieldIds;
}