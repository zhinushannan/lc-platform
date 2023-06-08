package io.github.zhinushannan.lcplatformback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhinushannan.lcplatformback.dto.condition.TableInfoPageCondition;
import io.github.zhinushannan.lcplatformback.dto.resp.FieldMetaInfoRespDto;
import io.github.zhinushannan.lcplatformback.dto.resp.TableInfoRespDto;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.TableMetaInfoException;
import io.github.zhinushannan.lcplatformback.mapper.FieldMetaInfoMapper;
import io.github.zhinushannan.lcplatformback.mapper.SystemInitializationMapper;
import io.github.zhinushannan.lcplatformback.mapper.TableMetaInfoMapper;
import io.github.zhinushannan.lcplatformback.service.BusinessService;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private TableMetaInfoMapper tableMetaInfoMapper;

    @Autowired
    private FieldMetaInfoMapper fieldMetaInfoMapper;

    @Autowired
    private SystemInitializationMapper systemInitializationMapper;

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @Autowired
    private FieldMetaInfoService fieldMetaInfoService;


    @Override
    public IPage<TableInfoRespDto> page(TableInfoPageCondition condition) {
        Page<TableMetaInfo> page = condition.getPage();
        QueryWrapper<TableMetaInfo> wrapper = condition.getQueryWrapper();
        tableMetaInfoService.page(page, wrapper);
        return page.convert(tableMetaInfo -> BeanUtil.copyProperties(tableMetaInfo, TableInfoRespDto.class));
    }

    @Override
    @Transactional
    public void delete(Long tableId) {
        TableMetaInfo tableMetaInfo = tableMetaInfoMapper.selectById(tableId);
        if (tableMetaInfo == null) {
            throw TableMetaInfoException.NOW_FOUND;
        }
        tableMetaInfoMapper.deleteById(tableId);
        fieldMetaInfoMapper.delete(new QueryWrapper<FieldMetaInfo>().eq("table_meta_info_id", tableId));
        systemInitializationMapper.dropTables("t_" + tableMetaInfo.getPhysicsTableSerial());
    }

    @Override
    public List<FieldMetaInfoRespDto> getFieldsByTableId(Long tableId) {

        TableMetaInfo tableMetaInfo = tableMetaInfoService.getById(tableId);
        if (tableMetaInfo == null) {
            throw TableMetaInfoException.NOW_FOUND;
        }
        List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list(new QueryWrapper<FieldMetaInfo>().eq("table_meta_info_id", tableId));
        return BeanUtil.copyToList(fieldMetaInfos, FieldMetaInfoRespDto.class);
    }

}
