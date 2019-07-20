package com.honji.exhibition.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private String arrivedTravelMode;

    /**
     * 返程出行方式
     */
    private Integer leavedTravelMode;

    /**
     * 来程班次号
     */
    private String arrivedNum;

    /**
     * 返程班次号
     */
    private String leavedNum;

    private LocalDateTime arrivedTime;

    private LocalDateTime leavedTime;

    /**
     * 来程接送站点
     */
    private Integer arrivedPickUpStation;

    /**
     * 返程接送地点
     */
    private Integer leavedPickUpLocation;

    /**
     * 返程站点
     */
    private Integer leavedStation;


}
