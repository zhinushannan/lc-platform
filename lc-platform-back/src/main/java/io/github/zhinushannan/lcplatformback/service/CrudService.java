package io.github.zhinushannan.lcplatformback.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.zhinushannan.lcplatformback.bean.ConditionDto;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;

import java.util.List;
import java.util.Map;

public interface CrudService {
    ResultBean<String> save(String tableLogicName, JSONObject jsonObject);

    ResultBean<String> update(String tableLogicName, JSONObject jsonObject);

    ResultBean<String> delete(String tableLogicName, List<Long> ids);

    IPage<Map<String, Object>> page(String tableLogicName, ConditionDto condition);

    ResultBean<List<String>> listKeyword(String tableLogicName, JSONObject jsonObject);
}
