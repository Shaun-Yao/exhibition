package com.honji.exhibition.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LeaveStationEnum {

    NONE(0, "无"),
    AIRPORT(1, "潮汕揭阳机场"),
    HIGH_SPEED_RAIL_STATION(2, "潮汕高铁站"),
    TRAIN_STATION(3, "潮州火车站");

    LeaveStationEnum(int code, String desc) {
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
