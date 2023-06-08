package io.github.zhinushannan.lcplatformback.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.zhinushannan.lcplatformback.dto.condition.TableInfoPageCondition;
import io.github.zhinushannan.lcplatformback.dto.resp.FieldMetaInfoRespDto;
import io.github.zhinushannan.lcplatformback.dto.resp.TableInfoRespDto;

import java.util.List;

public interface BusinessService {

    /**
     * 分页查询业务列表（数据表列表）
     */
    IPage<TableInfoRespDto> page(TableInfoPageCondition condition);

    /**
     * 删除业务（数据表）
     */
    void delete(Long tableId);

    /**
     * 根据表id，获取 字段列表
     */
    List<FieldMetaInfoRespDto> getFieldsByTableId(Long tableId);
}
