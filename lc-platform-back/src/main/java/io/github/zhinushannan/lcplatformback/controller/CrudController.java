package io.github.zhinushannan.lcplatformback.controller;

import cn.hutool.json.JSONObject;
import io.github.zhinushannan.lcplatformback.bean.ConditionDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("crud")
public class CrudController {

    @PostMapping("/{table_logic_name}")
    public String save(@PathVariable("table_logic_name") String tableLogicName,
                       @RequestBody JSONObject jsonObject) {
        return null;
    }

    @PutMapping("/{table_logic_name}")
    public String update(@PathVariable("table_logic_name") String tableLogicName,
                         @RequestBody JSONObject jsonObject) {
        return null;
    }

    @PostMapping("/{table_logic_name}/page")
    public String page(@PathVariable("table_logic_name") String tableLogicName,
                       @RequestBody ConditionDto condition) {
        return null;
    }

    @DeleteMapping("/{table_logic_name}")
    public String delete(@PathVariable("table_logic_name") String tableLogicName,
                         @RequestBody List<Long> ids) {
        return null;
    }

}
