package com.honji.exhibition.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.honji.exhibition.entity.Room;
import com.honji.exhibition.entity.RoomParticipant;
import com.honji.exhibition.mapper.RoomMapper;
import com.honji.exhibition.mapper.RoomParticipantMapper;
import com.honji.exhibition.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        roomMapper.insert(room);
        for (RoomParticipant roomParticipant : room.getParticipants()) {
            roomParticipant.setRoomId(room.getId());
            roomParticipantMapper.insert(roomParticipant);
        }
    }
}
