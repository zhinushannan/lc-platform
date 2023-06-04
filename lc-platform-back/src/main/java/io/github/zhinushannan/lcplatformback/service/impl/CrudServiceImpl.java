package io.github.zhinushannan.lcplatformback.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhinushannan.lcplatformback.bean.ConditionDto;
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
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public IPage<Map<String, Object>> page(String tableLogicName, ConditionDto condition) {
        TableMetaInfo tableMetaInfo = Cache.getTableMetaInfoByTableLogicName(tableLogicName);

        List<FieldMetaInfo> fieldMetaInfos = Cache.getFieldMetaInfoByTableLogicName(tableLogicName);
        Map<String, String> phyLogicMap = fieldMetaInfos.stream().collect(Collectors.toMap(t -> "f_" + t.getPhysicsFieldSerial(), FieldMetaInfo::getLogicFieldName));

        Page<Map<String, Object>> page = condition.getPage();
        List<Map<String, Object>> data = crudMapper.list("t_" + tableMetaInfo.getPhysicsTableSerial(), phyLogicMap, (page.getSize() * (page.getCurrent() - 1)), page.getSize());
        long total = crudMapper.count("t_" + tableMetaInfo.getPhysicsTableSerial());

        Collection<String> logicNames = phyLogicMap.values();
        for (Map<String, Object> datum : data) {
            Set<String> keys = datum.keySet();
            for (String logicName : logicNames) {
                if (!keys.contains(logicName)) {
                    datum.put(logicName, null);
                    System.out.println("缺少 logicName : " + logicName);
                }
            }
        }

        page.setRecords(data);
        page.setTotal(total);
        System.out.println("data : " + JSONUtil.toJsonStr(data));
        System.out.println("total : " + total);

        return page;
    }
}
