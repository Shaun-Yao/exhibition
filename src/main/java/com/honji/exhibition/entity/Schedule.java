package com.honji.exhibition.entity;

import com.honji.exhibition.enums.LeavePickUpLocationEnum;
import com.honji.exhibition.enums.LeaveStationEnum;
import com.honji.exhibition.enums.StationEnum;
import com.honji.exhibition.enums.TravelModeEnum;
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
 * @since 2019-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Schedule extends BaseEntity {


    private Long userId;

    /**
     * 来程出行方式
     */
    private TravelModeEnum arrivedTravelMode;

    /**
     * 返程出行方式
     */
    private TravelModeEnum leavedTravelMode;

    /**
     * 来程班次号
     */
    private String arrivedNum;

    /**
     * 返程班次号
     */
    private String leavedNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivedTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime leavedTime;

    /**
     * 来程接送站点
     */
    private StationEnum arrivedPickUpStation;

    /**
     * 返程接送地点
     */
    private LeavePickUpLocationEnum leavedPickUpLocation;

    /**
     * 返程站点
     */
    private LeaveStationEnum leavedStation;

    /**
     * 班次图片1
     */
    private String picture1;

    /**
     * 班次图片2
     */
    private String picture2;

}
