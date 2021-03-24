package com.honji.exhibition.entity;

import com.honji.exhibition.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yao
 * @since 2019-07-07
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Participant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String name;

    private String mobile;

    //@EnumValue
    private SexEnum sex;

    /**
     * 是否参加培训
     */
    private boolean attendTraining;

    /**
     * 是否参加温泉
     */
    private boolean attendHotSpring;

}
