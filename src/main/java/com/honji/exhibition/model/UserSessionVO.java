package com.honji.exhibition.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 保存到session的用户信息
 */
@Data
@AllArgsConstructor
public class UserSessionVO {
    private Long id;
    private Long shopId;
    private String shopCode;
    private String shopType;

}
