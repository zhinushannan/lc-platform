package io.github.zhinushannan.lcplatformback.service;

import io.github.zhinushannan.lcplatformback.util.query.LcQueryWrapper;
import io.github.zhinushannan.lcplatformback.util.query.Page;

import java.util.List;
import java.util.Map;

public interface LcQueryService {

    List<Map<String, Object>> select(LcQueryWrapper wrapper);

    Page page(Page page, LcQueryWrapper wrapper);

}
