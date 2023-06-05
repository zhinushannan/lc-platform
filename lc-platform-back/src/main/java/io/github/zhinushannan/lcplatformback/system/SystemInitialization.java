package io.github.zhinushannan.lcplatformback.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.zhinushannan.lcplatformback.LcPlatformBackApplication;
import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.mapper.SystemInitializationMapper;
import io.github.zhinushannan.lcplatformback.service.FieldMetaInfoService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SystemInitialization implements InitializingBean, SmartLifecycle {

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @Autowired
    private FieldMetaInfoService fieldMetaInfoService;

    @Autowired
    private SystemInitializationMapper systemInitializationMapper;

    @Value("${system.initialization:false}")
    private Boolean isInitialization;

    private boolean isRunning = false;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        if (isInitialization) {
            List<String> tableNames = systemInitializationMapper.selectAllTableNames();
            for (String tableName : tableNames) {
                systemInitializationMapper.dropTables(tableName);
            }
            systemInitializationMapper.createTableMetaInfo();
            systemInitializationMapper.createFieldMetaInfo();

            log.info("系统初始化已完成，请将 system.initialization 修改为 false 后开始运营您的系统！");
            log.info("系统初始化已完成，请将 system.initialization 修改为 false 后开始运营您的系统！");
            log.info("系统初始化已完成，请将 system.initialization 修改为 false 后开始运营您的系统！");

            int exit = SpringApplication.exit(applicationContext, () -> 0);
            System.exit(exit);
        }


        log.info(" =====> 开始加载 LOGIC_TABLE_NAME_SERIAL ");
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getOne(new QueryWrapper<TableMetaInfo>().orderByDesc("create_time").last(" limit 1 "));
        SystemConstant.PHYSICS_TABLE_SERIAL.set(tableMetaInfo == null ? 0 : tableMetaInfo.getPhysicsTableSerial());
        log.info(" =====> 加载成功 LOGIC_TABLE_NAME_SERIAL = {} ", tableMetaInfo == null ? 0 : tableMetaInfo.getLogicTableName());

        refreshCache();

    }

    public void refreshCache() {
        synchronized (this) {
            log.info(" =====> 刷新表缓存 开始 ");
            List<TableMetaInfo> tableMetaInfos = tableMetaInfoService.list(new QueryWrapper<>());
            Cache.TABLE_META_INFO_MAP = tableMetaInfos.stream().collect(Collectors.toMap(TableMetaInfo::getLogicTableName, t -> t));
            Cache.TABLE_LOGIC_NAME = tableMetaInfos.stream().map(TableMetaInfo::getLogicTableName).collect(Collectors.toList());
            Cache.TABLE_BUSINESS_NAME = tableMetaInfos.stream().map(TableMetaInfo::getBusinessTableName).collect(Collectors.toList());
            log.info(" =====> 刷新表缓存 结束 ");

            log.info(" =====> 刷新字段缓存 开始 ");
            List<FieldMetaInfo> fieldMetaInfos = fieldMetaInfoService.list();
            Cache.FIELD_META_INFO_MAP = fieldMetaInfos.stream().collect(Collectors.groupingBy(FieldMetaInfo::getTableMetaInfoId));
            log.info(" =====> 刷新字段缓存 结束 ");
        }
    }

    @Override
    public void start() {
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
