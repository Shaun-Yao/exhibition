package com.honji.exhibition.entity;

import com.honji.exhibition.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class RoomParticipant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long roomId;

    private Long participantId;


}
