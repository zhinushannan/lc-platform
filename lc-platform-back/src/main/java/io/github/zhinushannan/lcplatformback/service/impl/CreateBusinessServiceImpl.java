package io.github.zhinushannan.lcplatformback.service.impl;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.mapper.CreateBusinessMapper;
import io.github.zhinushannan.lcplatformback.service.CreateBusinessService;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateBusinessServiceImpl implements CreateBusinessService {

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @Autowired
    private FieldMetaInfoService fieldMetaInfoService;

    @Autowired
    private CreateBusinessMapper createBusinessMapper;

    // todo 需要进行事务控制
    @Override
    public Boolean createBusinessService(TableMetaInfo tableMetaInfo, FieldMetaInfo fieldMetaInfo) {
        // todo 需要对两个实体类进行合法性校验
        tableMetaInfoService.save(tableMetaInfo);
        fieldMetaInfoService.save(fieldMetaInfo);

        String tableName = "";
        List<String> fieldDDL = new ArrayList<>();
        int line = createBusinessMapper.createNewTable(tableName, fieldDDL);

        return null;
    }
}
