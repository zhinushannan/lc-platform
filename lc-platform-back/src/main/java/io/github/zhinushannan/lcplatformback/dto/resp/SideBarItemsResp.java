package io.github.zhinushannan.lcplatformback.dto.resp;

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
        private String index;
        private String title;
    }

}
