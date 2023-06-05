package io.github.zhinushannan.lcplatformback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.mapper.FieldMetaInfoMapper;
import io.github.zhinushannan.lcplatformback.mapper.SystemInitializationMapper;
import io.github.zhinushannan.lcplatformback.mapper.TableMetaInfoMapper;
import io.github.zhinushannan.lcplatformback.service.BusinessService;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private TableMetaInfoMapper tableMetaInfoMapper;

    @Autowired
    private FieldMetaInfoMapper fieldMetaInfoMapper;

    @Autowired
    private SystemInitializationMapper systemInitializationMapper;

    @Override
    public void delete(Long tableId) {
        TableMetaInfo tableMetaInfo = tableMetaInfoMapper.selectById(tableId);
        tableMetaInfoMapper.deleteById(tableId);
        fieldMetaInfoMapper.delete(new QueryWrapper<FieldMetaInfo>().eq("table_meta_info_id", tableId));
        systemInitializationMapper.dropTables("t_" + tableMetaInfo.getPhysicsTableSerial());
    }
}
