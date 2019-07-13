package com.honji.exhibition.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public abstract class BaseEntity extends IdEntity {

    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createdTime;

    @TableField(fill = FieldFill.UPDATE)
    protected LocalDateTime updatedTime;
}
