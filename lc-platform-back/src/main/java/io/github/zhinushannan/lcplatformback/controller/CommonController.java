package io.github.zhinushannan.lcplatformback.controller;

import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.constant.MySQLTypeEnum;
import io.github.zhinushannan.lcplatformback.dto.resp.DBTypeEnumResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("common")
@RestController
public class CommonController {

    @GetMapping("db-type")
    public ResultBean<List<DBTypeEnumResp>> dbTypeList() {
        MySQLTypeEnum[] values = MySQLTypeEnum.values();
        List<DBTypeEnumResp> dbTypeEnumResps = Arrays.stream(values).map(e -> DBTypeEnumResp.builder()
                .jdbcType(e.getJdbcType())
                .javaType(e.getJavaType())
                .needLength(e.getNeedLength())
                .defaultLength(e.getDefaultLength())
                .build()).collect(Collectors.toList());
        return ResultBean.success(dbTypeEnumResps);
    }

}
