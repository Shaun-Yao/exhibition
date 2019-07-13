package com.honji.exhibition.entity;

import com.honji.exhibition.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yao
 * @since 2019-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Shop extends IdEntity {

    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String area;

    private String smallArea;


}
