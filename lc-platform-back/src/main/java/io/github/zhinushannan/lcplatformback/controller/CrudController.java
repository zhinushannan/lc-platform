package io.github.zhinushannan.lcplatformback.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.zhinushannan.lcplatformback.bean.ConditionDto;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("crud")
public class CrudController {

    @Autowired
    private CrudService crudService;

    @PostMapping("/{table_logic_name}")
    public ResultBean<String> save(@PathVariable("table_logic_name") String tableLogicName,
                           @RequestBody JSONObject jsonObject) {
        return crudService.save(tableLogicName, jsonObject);
    }

    @PutMapping("/{table_logic_name}")
    public String update(@PathVariable("table_logic_name") String tableLogicName,
                         @RequestBody JSONObject jsonObject) {
        return crudService.update(tableLogicName, jsonObject);
    }

    @PostMapping("/{table_logic_name}/page")
    public ResultBean<IPage<Map<String, Object>>> page(@PathVariable("table_logic_name") String tableLogicName,
                                                       @RequestBody ConditionDto condition) {
        IPage<Map<String, Object>> page = crudService.page(tableLogicName, condition);
        return ResultBean.success(page);
    }

    @DeleteMapping("/{table_logic_name}")
    public String delete(@PathVariable("table_logic_name") String tableLogicName,
                         @RequestBody List<Long> ids) {
        return crudService.delete(tableLogicName, ids);
    }

}
