package io.github.zhinushannan.lcplatformback.exception;

import io.github.zhinushannan.lcplatformback.bean.LowCodePlatformException;

public class LcQueryException extends LowCodePlatformException {
    public static final LcQueryException TABLE_NOT_FOUND = new LcQueryException("表不存在！");
    public static final LcQueryException FIELD_NOT_FOUND = new LcQueryException("字段不存在！");

    public LcQueryException(String message) {
        super(message);
    }
}
