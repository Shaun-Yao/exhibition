package com.honji.exhibition.service.impl;

import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.entity.Room;
import com.honji.exhibition.mapper.ParticipantMapper;
import com.honji.exhibition.service.IParticipantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yao
 * @since 2019-07-07
 */
@Service
public class ParticipantServiceImpl extends ServiceImpl<ParticipantMapper, Participant> implements IParticipantService {

    @Autowired
    private ParticipantMapper participantMapper;

    @Override
    public List<Participant> getAvailable(Long userId) {
        return participantMapper.selectAvailable(userId);
    }

    @Override
    public List<Participant> getUnAvailable(Long userId) {
        return participantMapper.selectUnAvailable(userId);
    }

    @Override
    public List<Participant> getByArea(Long userId) {
        return participantMapper.selectByArea(userId);
    }

    @Override
    public List<Participant> getChildren(Long userId) {
        return participantMapper.selectChildren(userId);
    }

    @Override
    public List<Participant> listByRoom(Long roomId) {
        return participantMapper.selectByRoom(roomId);
    }


}
