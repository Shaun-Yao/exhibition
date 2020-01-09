package com.honji.exhibition.model;

import com.honji.exhibition.enums.RoomTypeEnum;
import lombok.Data;

@Data
public class RoomVO {

    private Long id;
    private RoomTypeEnum type;
    private String userNames;

    public RoomVO(Long id, RoomTypeEnum type, String userNames) {
        this.id = id;
        this.type = type;
        this.userNames = userNames;
    }

}
