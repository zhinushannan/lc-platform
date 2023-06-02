package io.github.zhinushannan.lcplatformback.dto.resp;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectModeEnumResp {

    private Integer value;

    private String label;

}
