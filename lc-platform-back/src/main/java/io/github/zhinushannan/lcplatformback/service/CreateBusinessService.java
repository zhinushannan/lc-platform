package io.github.zhinushannan.lcplatformback.service;

import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.req.SelectShowFieldsReq;
import io.github.zhinushannan.lcplatformback.dto.req.TableInfoReq;

public interface CreateBusinessService {

    /**
     * 创建表的元数据
     */
    ResultBean<String> saveTableInfo(TableInfoReq req);

    Boolean createBusinessService(TableInfoReq dto);

    /**
     * 选择展示字段
     */
    ResultBean<String> selectShowFields(SelectShowFieldsReq req);
}
