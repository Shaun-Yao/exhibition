package com.honji.exhibition.entity;

import com.honji.exhibition.enums.ShopTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yao
 * @since 2019-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ScheduleTimeConfig extends IdEntity {


    private ShopTypeEnum shopType;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arriveStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arriveEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime leaveStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime leaveEndTime;

    /**
     * 截止日期说明
     */
    private String deadline;

    /**
     * 是否参与培训勾选框说明
     */
    private String trainingDesc;

    /**
     * 是否参与温泉勾选框说明
     */
    private String hotSpringDesc;

}
