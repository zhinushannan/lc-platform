package io.github.zhinushannan.lcplatformback.system;

import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class SystemConstant {

    protected static final AtomicInteger PHYSICS_TABLE_SERIAL = new AtomicInteger(0);

    /**
     * 原子的获取表逻辑名序号
     */
    public static Integer getNextLogicTableNameSerial() {
        return PHYSICS_TABLE_SERIAL.incrementAndGet();
    }

}
