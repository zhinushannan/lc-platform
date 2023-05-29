package io.github.zhinushannan.lcplatformback.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * mysql 数据类型
 */
@Getter
public enum MySQLTypeEnum {

    TINYINT("tinyint", Integer.class.getTypeName(), Integer.class, true, 4),
    INT("int", Integer.class.getTypeName(), Integer.class, true, 11),

    CHAR("char", String.class.getTypeName(), String.class, true, 1),
    VARCHAR("varchar", String.class.getTypeName(), String.class, true, 255),

    /*
    double
    decimal

    char
    varchar
    text

    date
    datetime
     */
    ;

    private final String jdbcType;

    private final String javaType;

    private final Class<?> javaTypeClass;

    private final Boolean needLength;

    private final Integer defaultLength;

    private static final Map<String, MySQLTypeEnum> JDBC_TYPE_ENUM = new HashMap<>();
    private static final Map<String, MySQLTypeEnum> JAVA_TYPE_ENUM = new HashMap<>();

    static {
        MySQLTypeEnum[] values = MySQLTypeEnum.values();
        for (MySQLTypeEnum value : values) {
            JDBC_TYPE_ENUM.put(value.getJdbcType(), value);
            JAVA_TYPE_ENUM.put(value.getJavaType(), value);
        }
    }

    MySQLTypeEnum(String jdbcType, String javaType, Class<?> javaTypeClass, Boolean needLength, Integer defaultLength) {
        this.jdbcType = jdbcType;
        this.javaType = javaType;
        this.javaTypeClass = javaTypeClass;
        this.needLength = needLength;
        this.defaultLength = defaultLength;
    }
}
