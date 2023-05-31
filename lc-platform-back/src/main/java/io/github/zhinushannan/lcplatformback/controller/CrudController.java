package io.github.zhinushannan.lcplatformback.controller;

import cn.hutool.json.JSONObject;
import io.github.zhinushannan.lcplatformback.bean.ConditionDto;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String page(@PathVariable("table_logic_name") String tableLogicName,
                       @RequestBody ConditionDto condition) {
        return null;
    }

    @DeleteMapping("/{table_logic_name}")
    public String delete(@PathVariable("table_logic_name") String tableLogicName,
                         @RequestBody List<Long> ids) {
        return crudService.delete(tableLogicName, ids);
    }

}
