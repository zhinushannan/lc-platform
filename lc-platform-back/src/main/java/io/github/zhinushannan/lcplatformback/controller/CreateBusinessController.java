package io.github.zhinushannan.lcplatformback.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("create-business")
public class CreateBusinessController {

    /**
     * 新建一个业务
     * 需要提供：
     * 表信息：逻辑名称、业务名称
     * 字段信息：逻辑名称、业务名称、字段类型、长度
     * 页面信息：展示字段、搜索字段
     */
    @PostMapping("")
    public void create() {

    }

}
