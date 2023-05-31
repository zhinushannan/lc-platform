package io.github.zhinushannan.lcplatformback.controller;

import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.req.TableInfoReq;
import io.github.zhinushannan.lcplatformback.service.CreateBusinessService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("create-business")
public class CreateBusinessController {

    @Autowired
    private CreateBusinessService createBusinessService;

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    /**
     * 用于检测表逻辑名是否合法（是否唯一）
     */
    @GetMapping("check-table-logic")
    public ResultBean<String> checkTableLogic(@RequestParam("logicName") String logicName) {
        return tableMetaInfoService.checkTableLogic(logicName);
    }

    /**
     * 用于检测表业务名是否合法（是否唯一）
     */
    @GetMapping("check-table-business")
    public ResultBean<String> checkTableBusiness(@RequestParam("businessName") String businessName) {
        return tableMetaInfoService.checkTableBusiness(businessName);
    }

    /**
     * 创建表的元数据
     */
    @PostMapping("save-table-info")
    public ResultBean<String> saveTableInfo(@RequestBody TableInfoReq req) {
        return createBusinessService.saveTableInfo(req);
    }

    /**
     * 新建一个业务
     * 需要提供：
     * 表信息：逻辑名称、业务名称
     * 字段信息：逻辑名称、业务名称、字段类型、长度
     * 页面信息：展示字段、搜索字段
     */
    @PostMapping("")
    public void create(@RequestBody TableInfoReq dto) {
        createBusinessService.createBusinessService(dto);
    }

}
