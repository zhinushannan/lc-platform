package io.github.zhinushannan.lcplatformback.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SystemInitialization implements InitializingBean {

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @Autowired
    private FieldMetaInfoService fieldMetaInfoService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(" =====> 开始加载 LOGIC_TABLE_NAME_SERIAL ");
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getOne(new QueryWrapper<TableMetaInfo>().orderByDesc("create_time").last(" limit 1 "));
        SystemConstant.PHYSICS_TABLE_SERIAL.set(tableMetaInfo == null ? 0 : tableMetaInfo.getPhysicsTableSerial());
        log.info(" =====> 加载成功 LOGIC_TABLE_NAME_SERIAL = {} ", tableMetaInfo == null ? 0 : tableMetaInfo.getLogicTableName());

        refreshCache();

    }

    public void refreshCache() {
        synchronized (this) {
            log.info(" =====> 刷新表缓存 开始 ");
            List<TableMetaInfo> tableMetaInfos = tableMetaInfoService.list();
            Cache.TABLE_META_INFO_MAP = tableMetaInfos.stream().collect(Collectors.toMap(TableMetaInfo::getLogicTableName, t -> t));
            log.info(" =====> 刷新表缓存 结束 ");

            log.info(" =====> 刷新字段缓存 开始 ");
            List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list();
            Cache.FIELD_META_INFO_MAP = fieldMetaInfos.stream().collect(Collectors.groupingBy(FieldMetaInfo::getTableMetaInfoId));
            log.info(" =====> 刷新字段缓存 结束 ");
        }
    }

}
