package io.github.zhinushannan.lcplatformback.constant;

import io.github.zhinushannan.lcplatformback.exception.EnumException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SelectModeEnum {

    DISABLE(0, "不提供搜索"),
    SELECT(1, "选择搜索"),
    FUZZY(2, "模糊匹配"),
    PRECISE(3, "精确匹配"),
    ;

    private final Integer value;

    private final String label;

    SelectModeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<Integer, SelectModeEnum> VALUE_LABEL_MAP = new HashMap<>();

    static {
        SelectModeEnum[] values = SelectModeEnum.values();
        for (SelectModeEnum value : values) {
            VALUE_LABEL_MAP.put(value.getValue(), value);
        }
    }

    public static SelectModeEnum getLabelByValue(Integer value) {
        SelectModeEnum selectModeEnum = VALUE_LABEL_MAP.get(value);
        if (selectModeEnum == null) {
            throw EnumException.SELECT_MODE_NOT_FOUND;
        }
        return selectModeEnum;
    }

}
