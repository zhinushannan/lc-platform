package io.github.zhinushannan.lcplatformback.exception;

import io.github.zhinushannan.lcplatformback.bean.LowCodePlatformException;

public class PathBindException extends LowCodePlatformException {

    public static final PathBindException NOT_FOUNT = new PathBindException("目录或路径不存在");

    public static final PathBindException DIR_NAME_BLANK = new PathBindException("目录名称为空");
    public static final PathBindException DIR_PATH_BLANK = new PathBindException("目录路径为空");

    public static final PathBindException NOT_IS_DIR = new PathBindException("不是路径");
    public static final PathBindException NOT_IS_PATH = new PathBindException("不是目录");

    public static final PathBindException PARENT_ID_NULL = new PathBindException("父级目录ID为空");

    public PathBindException(String message) {
        super(message);
    }
}
