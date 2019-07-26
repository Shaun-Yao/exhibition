package com.honji.exhibition.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.honji.exhibition.enums.RoomTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yao
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Room extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private RoomTypeEnum type;

    @TableField(exist = false)
    private List<Participant> participants = new ArrayList<>();
}
