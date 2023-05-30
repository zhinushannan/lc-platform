package io.github.zhinushannan.lcplatformback.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class CreateBusinessDto {

    private TableInfo tableInfo;
    private List<FieldInfo> fieldInfos;
    private PageInfo pageInfo;

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
    }

    @Data
    public static class PageInfo {
        private List<String> showField;
        private List<String> searchField;
    }

}
