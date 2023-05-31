package io.github.zhinushannan.lcplatformback.service;

import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.req.SelectEnableFieldsReq;
import io.github.zhinushannan.lcplatformback.dto.req.TableInfoReq;

public interface CreateBusinessService {

    /**
     * 创建表的元数据
     */
    ResultBean<String> saveTableInfo(TableInfoReq req);

    /**
     * 选择展示字段
     */
    ResultBean<String> selectShowFields(SelectEnableFieldsReq req);

    /**
     * 选择搜索字段
     */
    ResultBean<String> selectSearchFields(SelectEnableFieldsReq req);

    /**
     * 创建物理表
     */
    ResultBean<String> createPhysicsTable(Long tableId);
}
