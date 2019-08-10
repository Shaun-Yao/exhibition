package com.honji.exhibition.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.RoomParticipant;
import com.honji.exhibition.service.IRoomParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yao
 * @since 2019-07-24
 */
@Controller
@RequestMapping("/room-participant")
public class RoomParticipantController {

    @Autowired
    private IRoomParticipantService roomParticipantService;

    @ResponseBody
    @GetMapping("/get")
    public boolean get(@RequestParam Long participantId) {
        QueryWrapper<RoomParticipant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("participant_id", participantId);
        RoomParticipant roomParticipant = roomParticipantService.getOne(queryWrapper);
        if (roomParticipant == null) {
            return true;
        }
        return false;
    }
}
