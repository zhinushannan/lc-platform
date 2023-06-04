package io.github.zhinushannan.lcplatformback.exception;

import io.github.zhinushannan.lcplatformback.bean.LowCodePlatformException;

public class CrudException extends LowCodePlatformException {

    public static final CrudException NULL_NOT_ALLOW_FIELD_NULL = new CrudException("存在值为空的不允许为空字段！");

    public CrudException(String message) {
        super(message);
    }
}
