package com.honji.exhibition.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomTypeEnum {

    BIG_BED(1, "大床"),
    DOUBLE_BED(2, "双床");

    RoomTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }


}
