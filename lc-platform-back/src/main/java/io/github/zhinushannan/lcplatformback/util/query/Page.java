package io.github.zhinushannan.lcplatformback.util.query;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Page {

    public static Page of(long current, long size) {
        Page page = new Page();
        page.setCurrent(current);
        page.setSize(size);
        return page;
    }

    private long current = 1L;

    private long size = 10L;

    private long total = 0L;

    private List<Map<String, Object>> records;

}
