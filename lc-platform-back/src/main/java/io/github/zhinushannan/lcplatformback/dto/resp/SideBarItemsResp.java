package io.github.zhinushannan.lcplatformback.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SideBarItemsResp {

    private String icon;

    private String index;

    private String title;

    private List<SubSideBar> subs;

    @Data
    @Builder
    public static class SubSideBar {
        @JsonSerialize(using = ToStringSerializer.class)
        private Long tableId;
        private String index;
        private String title;
    }

}
