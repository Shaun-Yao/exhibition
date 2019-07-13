package com.honji.exhibition.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女"),
    CHILD(3, "儿童");

    SexEnum (int code, String desc) {
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
    public String getdesc() {
        return desc;
    }


}
