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
import io.github.zhinushannan.lcplatformback.service.BusinessService;
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

    @Autowired
    private BusinessService businessService;

    @PostMapping("page")
    public ResultBean<IPage<TableInfoRespDto>> page(@RequestBody TableInfoPageCondition condition) {
        Page<TableMetaInfo> page = condition.getPage();
        QueryWrapper<TableMetaInfo> wrapper = condition.getQueryWrapper();
        tableMetaInfoService.page(page, wrapper);
        IPage<TableInfoRespDto> convert = page.convert(tableMetaInfo -> BeanUtil.copyProperties(tableMetaInfo, TableInfoRespDto.class));
        return ResultBean.success(convert);
    }

    @DeleteMapping("delete")
    public ResultBean<String> delete(@RequestParam("tableId") Long tableId) {
        businessService.delete(tableId);
        return ResultBean.success("删除成功！");
    }


    @GetMapping("fields")
    public ResultBean<List<FieldMetaInfoRespDto>> fieldsById(@RequestParam("tableId") Long tableId) {
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getById(tableId);
        if (tableMetaInfo == null) {
            return ResultBean.notFound("表不存在！");
        }
        List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list(new QueryWrapper<FieldMetaInfo>().eq("table_meta_info_id", tableId));
        List<FieldMetaInfoRespDto> resp = BeanUtil.copyToList(fieldMetaInfos, FieldMetaInfoRespDto.class);
        return ResultBean.success(resp);
    }

    @GetMapping("fields-by-table-logic")
    public ResultBean<List<FieldMetaInfoRespDto>> fieldsByTableLogicName(@RequestParam("tableLogicName") String tableLogicName) {
        List<TableMetaInfo> tableMetaInfos = tableMetaInfoService.list(new QueryWrapper<TableMetaInfo>().eq("logic_table_name", tableLogicName));
        if (tableMetaInfos == null || tableMetaInfos.isEmpty()) {
            return ResultBean.notFound("表不存在！");
        }
        TableMetaInfo tableMetaInfo = tableMetaInfos.get(0);
        List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list(new QueryWrapper<FieldMetaInfo>()
                .eq("table_meta_info_id", tableMetaInfo.getId())
        );
        List<FieldMetaInfoRespDto> resp = BeanUtil.copyToList(fieldMetaInfos, FieldMetaInfoRespDto.class);
        return ResultBean.success(resp);
    }

}
