package io.github.zhinushannan.lcplatformback.exception;

import io.github.zhinushannan.lcplatformback.bean.LowCodePlatformException;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;

public class TableMetaInfoException extends LowCodePlatformException {

    public static final TableMetaInfoException NOW_FOUND = new TableMetaInfoException("表不存在！");

    public TableMetaInfoException(String message) {
        super(message);
    }
}
