package io.github.zhinushannan.lcplatformback.controller;

import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.req.SelectEnableFieldsReq;
import io.github.zhinushannan.lcplatformback.dto.req.TableInfoReq;
import io.github.zhinushannan.lcplatformback.service.CreateBusinessService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * todo 1、字段名唯一性校验
 * todo 2、锁机制优化
 * todo 3、缓存优化
 */
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
     * 选择展示字段
     */
    @PostMapping("select-show-field")
    public ResultBean<String> selectShowFields(@RequestBody SelectEnableFieldsReq req) {
        return createBusinessService.selectShowFields(req);
    }

    /**
     * 选择搜索字段
     */
    @PostMapping("select-search-field")
    public ResultBean<String> selectSearchFields(@RequestBody SelectEnableFieldsReq req) {
        return createBusinessService.selectSearchFields(req);
    }

    /**
     * 创建物理表
     */
    @GetMapping("create-physics-table")
    public ResultBean<String> createPhysicsTable(@RequestParam("tableId") Long tableId) {
        return createBusinessService.createPhysicsTable(tableId);
    }

}
