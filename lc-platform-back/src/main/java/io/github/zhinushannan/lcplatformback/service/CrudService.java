package io.github.zhinushannan.lcplatformback.service;

import cn.hutool.json.JSONObject;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;

import java.util.List;

public interface CrudService {
    ResultBean<String> save(String tableLogicName, JSONObject jsonObject);

    String update(String tableLogicName, JSONObject jsonObject);

    String delete(String tableLogicName, List<Long> ids);
}
