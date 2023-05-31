package io.github.zhinushannan.lcplatformback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.mapper.TableMetaInfoMapper;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import io.github.zhinushannan.lcplatformback.system.Cache;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhinushannan
 * @since 2023-05-29
 */
@Service
public class TableMetaInfoServiceImpl extends ServiceImpl<TableMetaInfoMapper, TableMetaInfo> implements TableMetaInfoService {

    @Override
    public ResultBean<String> checkTableLogic(String tableLogicName) {
        Boolean logicNameExist = Cache.hasTableLogicNameExist(tableLogicName);
        return logicNameExist ? ResultBean.error("该逻辑表名已存在！") : ResultBean.success();
    }

    @Override
    public ResultBean<String> checkTableBusiness(String businessName) {
        Boolean businessNameExist = Cache.hasTableBusinessNameExist(businessName);
        return businessNameExist ? ResultBean.error("该业务表名已存在！") : ResultBean.success();
    }
}
