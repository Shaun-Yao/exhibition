package com.honji.exhibition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.entity.Room;
import com.honji.exhibition.entity.RoomParticipant;
import com.honji.exhibition.mapper.RoomMapper;
import com.honji.exhibition.mapper.RoomParticipantMapper;
import com.honji.exhibition.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yao
 * @since 2019-07-24
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomParticipantMapper roomParticipantMapper;

    @Override
    @Transactional
    public void add(Room room) {
        //List<RoomParticipant> roomParticipants = new ArrayList<>();
        roomMapper.insert(room);

        for (Participant participant : room.getParticipants()) {
            Long participantId = participant.getId();
            if (participantId == null) {
                continue;
            }
            RoomParticipant roomParticipant = new RoomParticipant();
            roomParticipant.setRoomId(room.getId());
            roomParticipant.setParticipantId(participant.getId());
            //roomParticipants.add(roomParticipant);
            roomParticipantMapper.insert(roomParticipant);
        }
    }

    @Override
    @Transactional
    public void merge(Room room) {

        QueryWrapper<RoomParticipant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", room.getId());
        //List<RoomParticipant> roomParticipants = roomParticipantMapper.selectList(queryWrapper);
        roomParticipantMapper.delete(queryWrapper);
        roomMapper.updateById(room);

        for (Participant participant : room.getParticipants()) {
            Long participantId = participant.getId();
            if (participantId == null) {
                continue;
            }
            RoomParticipant roomParticipant = new RoomParticipant();
            roomParticipant.setRoomId(room.getId());
            roomParticipant.setParticipantId(participant.getId());
            roomParticipantMapper.insert(roomParticipant);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        QueryWrapper<RoomParticipant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", id);
        roomParticipantMapper.delete(queryWrapper);
        roomMapper.deleteById(id);
    }
}
