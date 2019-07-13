package com.honji.exhibition.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseEntity {
    private String openId;
    private String shopCode;
    //private String shopName;
    //private String area;

    @TableField(exist = false)
    private List<Participant> participants = new ArrayList<>();


}
