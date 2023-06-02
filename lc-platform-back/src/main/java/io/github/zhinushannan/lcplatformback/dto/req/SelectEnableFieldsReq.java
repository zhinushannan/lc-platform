package io.github.zhinushannan.lcplatformback.dto.req;

import lombok.Data;

@Data
public class SelectEnableFieldsReq {

    private Long tableId;

    private Long fieldId;

    private Boolean showEnable;

    private Integer selectMode;

}
