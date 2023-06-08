package io.github.zhinushannan.lcplatformback.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class TableInfoReq {

    private TableInfo tableInfo;
    private List<FieldInfo> fieldInfos;

    @Data
    public static class TableInfo {
        private String tableLogicName;
        private String tableBusinessName;
    }

    @Data
    public static class FieldInfo {
        private String fieldLogicName;
        private String fieldBusinessName;
        private String fieldJdbcType;
        private Integer fieldJdbcLength;
        private Boolean nullable;
        private Boolean enableShow;
    }

}
