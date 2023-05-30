package io.github.zhinushannan.lcplatformback.util;

public class DBConvert {

    public static String tableNameConvertBySerial(Integer serial) {
        return "t_" + serial;
    }

    public static String fieldNameConvertBySerial(Integer serial) {
        return "f_" + serial;
    }

}
