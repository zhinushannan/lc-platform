package io.github.zhinushannan.lcplatformback.service.impl;

import io.github.zhinushannan.lcplatformback.mapper.LcQueryMapper;
import io.github.zhinushannan.lcplatformback.service.LcQueryService;
import io.github.zhinushannan.lcplatformback.util.query.LcQueryWrapper;
import io.github.zhinushannan.lcplatformback.util.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LcQueryServiceImpl implements LcQueryService {

    @Autowired
    private LcQueryMapper lcQueryMapper;

    @Override
    public List<Map<String, Object>> select(LcQueryWrapper wrapper) {
        wrapper.setPage(null);
        String sql = wrapper.getSql();
        return lcQueryMapper.select(sql);
    }

    @Override
    public Page page(Page page, LcQueryWrapper wrapper) {
        wrapper.setPage(page);
        String sqlCount = wrapper.getSqlCount();
        String sql = wrapper.getSql();

        long count = lcQueryMapper.count(sqlCount);
        List<Map<String, Object>> select = lcQueryMapper.select(sql);

        page.setTotal(count);
        page.setRecords(select);

        return page;
    }
}
