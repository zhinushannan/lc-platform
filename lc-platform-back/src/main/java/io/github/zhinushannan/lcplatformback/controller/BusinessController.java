package io.github.zhinushannan.lcplatformback.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.condition.TableInfoPageCondition;
import io.github.zhinushannan.lcplatformback.dto.resp.FieldMetaInfoRespDto;
import io.github.zhinushannan.lcplatformback.dto.resp.TableInfoRespDto;
import io.github.zhinushannan.lcplatformback.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("business")
@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    /**
     * 分页查询业务列表（数据表列表）
     */
    @PostMapping("page")
    public ResultBean<IPage<TableInfoRespDto>> page(@RequestBody TableInfoPageCondition condition) {
        IPage<TableInfoRespDto> page = businessService.page(condition);
        return ResultBean.success(page);
    }

    /**
     * 删除业务（数据表）
     */
    @DeleteMapping("delete")
    public ResultBean<String> delete(@RequestParam("tableId") Long tableId) {
        businessService.delete(tableId);
        return ResultBean.success("删除成功！");
    }

    /**
     * 根据表id，获取 字段列表
     */
    @GetMapping("fields")
    public ResultBean<List<FieldMetaInfoRespDto>> fieldsById(@RequestParam("tableId") Long tableId) {
        List<FieldMetaInfoRespDto> list = businessService.getFieldsByTableId(tableId);
        return ResultBean.success(list);
    }

}
