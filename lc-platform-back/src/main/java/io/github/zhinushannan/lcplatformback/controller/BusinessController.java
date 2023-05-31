package io.github.zhinushannan.lcplatformback.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.condition.TableInfoPageCondition;
import io.github.zhinushannan.lcplatformback.dto.resp.FieldMetaInfoRespDto;
import io.github.zhinushannan.lcplatformback.dto.resp.TableInfoRespDto;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("business")
@RestController
public class BusinessController {

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @Autowired
    private FieldMetaInfoService fieldMetaInfoService;

    @PostMapping("page")
    public ResultBean<IPage<TableInfoRespDto>> page(@RequestBody TableInfoPageCondition condition) {
        Page<TableMetaInfo> page = condition.getPage();
        QueryWrapper<TableMetaInfo> wrapper = condition.getQueryWrapper();
        tableMetaInfoService.page(page, wrapper);
        IPage<TableInfoRespDto> convert = page.convert(tableMetaInfo -> BeanUtil.copyProperties(tableMetaInfo, TableInfoRespDto.class));
        return ResultBean.success(convert);
    }

    @GetMapping("fields")
    public ResultBean<List<FieldMetaInfoRespDto>> fields(@RequestParam("tableId") Long tableId) {
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getById(tableId);
        if (tableMetaInfo == null) {
            return ResultBean.notFound("表不存在！");
        }
        List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list(new QueryWrapper<FieldMetaInfo>().eq("table_meta_info_id", tableId));
        List<FieldMetaInfoRespDto> resp = BeanUtil.copyToList(fieldMetaInfos, FieldMetaInfoRespDto.class);
        return ResultBean.success(resp);
    }

}
