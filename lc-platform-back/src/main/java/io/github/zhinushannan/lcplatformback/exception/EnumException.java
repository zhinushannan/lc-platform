package io.github.zhinushannan.lcplatformback.exception;

import io.github.zhinushannan.lcplatformback.bean.LowCodePlatformException;

public class EnumException extends LowCodePlatformException {

    public static final EnumException SELECT_MODE_NOT_FOUND = new EnumException("搜索模式不存在！");

    public EnumException(String message) {
        super(message);
    }
}
