package io.github.zhinushannan.lcplatformback.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.CrudException;
import io.github.zhinushannan.lcplatformback.mapper.CrudMapper;
import io.github.zhinushannan.lcplatformback.service.CrudService;
import io.github.zhinushannan.lcplatformback.system.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrudServiceImpl implements CrudService {

    @Autowired
    private CrudMapper crudMapper;


    @Override
    public ResultBean<String> save(String tableLogicName, JSONObject jsonObject) {
        System.out.println(tableLogicName);
        System.out.println(jsonObject);

        TableMetaInfo tableMetaInfo = Cache.getTableMetaInfoByTableLogicName(tableLogicName);
        List<FieldMetaInfo> fieldMetaInfos = Cache.getFieldMetaInfosByTableId(tableMetaInfo.getId());

        List<String> fields = new ArrayList<String>() {{
            add("id");
            add("create_time");
            add("update_time");
            add("deleted");
        }};
        LocalDateTime now = LocalDateTime.now();
        List<Object> values = new ArrayList<Object>() {{
            add(IdUtil.getSnowflakeNextId());
            add(now);
            add(now);
            add(0);
        }};

        for (FieldMetaInfo fieldMetaInfo : fieldMetaInfos) {
            String physicsFieldName = "f_" + fieldMetaInfo.getPhysicsFieldSerial();
            Boolean nullable = fieldMetaInfo.getNullable();
            Object val = jsonObject.get(fieldMetaInfo.getLogicFieldName());
            if (!nullable || ObjectUtil.isEmpty(val)) {
                throw CrudException.NULL_NOT_ALLOW_FIELD_NULL;
            }
            fields.add(physicsFieldName);
            values.add(val);
        }

        crudMapper.save("t_" + tableMetaInfo.getPhysicsTableSerial(), fields, values);
        return null;
    }

    @Override
    public String update(String tableLogicName, JSONObject jsonObject) {
        return null;
    }

    @Override
    public String delete(String tableLogicName, List<Long> ids) {
        return null;
    }
}
