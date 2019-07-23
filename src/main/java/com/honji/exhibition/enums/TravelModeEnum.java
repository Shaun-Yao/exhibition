package com.honji.exhibition.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

public enum TravelModeEnum implements IEnum<Integer> {
    PLANE(1, "飞机"),
    HIGH_SPEED_RAIL(2, "高铁"),
    TRAIN(3, "火车"),
    SELF_DRIVING(4, "自驾"),
    BUS(5, "大巴"),
    ;


    private int value;
    @Getter
    private String desc;

    TravelModeEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }


}
