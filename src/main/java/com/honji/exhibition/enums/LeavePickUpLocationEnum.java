package com.honji.exhibition.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LeavePickUpLocationEnum {

    NONE(0, "无需接送"),
    HOTEL(1, "酒店出发");
    //COMPANY(2, "公司出发");

    LeavePickUpLocationEnum(int code, String desc) {
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
