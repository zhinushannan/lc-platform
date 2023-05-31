package io.github.zhinushannan.lcplatformback.service.impl;

import cn.hutool.json.JSONObject;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudServiceImpl implements CrudService {
    @Override
    public ResultBean<String> save(String tableLogicName, JSONObject jsonObject) {
        return null;
    }

    @Override
    public String update(String tableLogicName, JSONObject jsonObject) {
        return null;
    }

    @Override
    public String delete(String tableLogicName, List<Long> ids) {
        return null;
    }
}
