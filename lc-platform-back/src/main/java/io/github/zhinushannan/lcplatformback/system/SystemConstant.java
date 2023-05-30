package io.github.zhinushannan.lcplatformback.system;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class SystemConstant {

    protected static final AtomicInteger PHYSICS_TABLE_SERIAL = new AtomicInteger(0);

    /**
     * 原子的获取表逻辑名序号
     */
    public static Integer getNextLogicTableNameSerial() {
        return PHYSICS_TABLE_SERIAL.incrementAndGet();
    }

}
