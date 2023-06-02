package io.github.zhinushannan.lcplatformback.exception;

import io.github.zhinushannan.lcplatformback.bean.LowCodePlatformException;

public class CreateBusinessException extends LowCodePlatformException {

    public static final CreateBusinessException TABLE_LOGIC_OR_BUSINESS_REPEAT = new CreateBusinessException("表逻辑名或业务名重复！");

    public static final CreateBusinessException TABLE_NOTFOUND = new CreateBusinessException("表不存在！");

    public static final CreateBusinessException FIELD_NULL = new CreateBusinessException("至少填写一个字段！");
    public static final CreateBusinessException FIELD_ERROR = new CreateBusinessException("字段选择有误！");
    public static final CreateBusinessException FIELD_LOGIC_REPEAT = new CreateBusinessException("字段逻辑名重复！");
    public static final CreateBusinessException FIELD_BUSINESS_REPEAT = new CreateBusinessException("字段业务名重复！");

    public CreateBusinessException(String message) {
        super(message);
    }
}
