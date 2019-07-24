package com.honji.exhibition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.honji.exhibition.entity.Participant;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2019-07-07
 */
public interface IParticipantService extends IService<Participant> {

    List<Participant> getByArea(Long userId);
}
