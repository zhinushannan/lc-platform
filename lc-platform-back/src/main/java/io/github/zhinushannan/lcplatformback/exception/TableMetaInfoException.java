package io.github.zhinushannan.lcplatformback.exception;

import io.github.zhinushannan.lcplatformback.bean.LowCodePlatformException;

public class TableMetaInfoException extends LowCodePlatformException {

    public static final TableMetaInfoException LOGIC_OR_BUSINESS_REPEAT = new TableMetaInfoException("逻辑名或业务名重复！");

    public TableMetaInfoException(String message) {
        super(message);
    }
}
