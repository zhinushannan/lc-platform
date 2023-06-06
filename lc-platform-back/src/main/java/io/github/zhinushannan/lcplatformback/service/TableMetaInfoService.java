package io.github.zhinushannan.lcplatformback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhinushannan
 * @since 2023-05-29
 */
public interface TableMetaInfoService extends IService<TableMetaInfo> {

    /**
     * 用于检测表逻辑名是否合法（是否唯一）
     */
    ResultBean<String> checkTableLogic(String tableLogicName);

    /**
     * 用于检测表业务名是否合法（是否唯一）
     */
    ResultBean<String> checkTableBusiness(String businessName);

    TableMetaInfo getByIdWithAssertNull(Long tableMetaInfoId);
}
