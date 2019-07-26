package com.honji.exhibition.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.entity.Room;
import com.honji.exhibition.entity.RoomParticipant;
import com.honji.exhibition.service.IParticipantService;
import com.honji.exhibition.service.IRoomParticipantService;
import com.honji.exhibition.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yao
 * @since 2019-07-24
 */
@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IParticipantService participantService;

    @Autowired
    private IRoomParticipantService roomParticipantService;

    @Autowired
    private HttpSession session;

    @GetMapping("/toAdd")
    public String toAdd(Model model) {
        Long userId = (Long) session.getAttribute("userId");
        List<Participant> participants = participantService.getByArea(userId);
        List<Participant> children = participantService.getChildren(userId);
        model.addAttribute("participants", participants);
        model.addAttribute("children", children);
        return "roomForm";
    }

    @GetMapping("/toEdit")
    public String toEdit(@RequestParam Long id, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        Room room = roomService.getById(id);
        List<Participant> roomParticipants = participantService.listByRoom(room.getId());
        room.setParticipants(roomParticipants);

        List<Participant> participants = participantService.getByArea(userId);
        List<Participant> children = participantService.getChildren(userId);
        //修改的时候要把已经选的两个人员加进列表
        participants.add(roomParticipants.get(0));
        participants.add(roomParticipants.get(1));
        if (roomParticipants.size() == 3) {//如果有第三个人员则加进儿童列表
            children.add(roomParticipants.get(2));
        }

        model.addAttribute("participants", participants);
        model.addAttribute("children", children);
        model.addAttribute("room", room);
        return "roomForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Room room) {
        if (room.getId() == null) {
            roomService.add(room);
        } else {
            roomService.merge(room);
        }

        return "redirect:/room/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        Long userId = (Long) session.getAttribute("userId");

        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Room> rooms = roomService.list(queryWrapper);
        for (Room room : rooms) {
            QueryWrapper<RoomParticipant> qw = new QueryWrapper<>();
            qw.eq("room_id", room.getId());
            List<RoomParticipant> roomParticipants = roomParticipantService.list(qw);
            for (RoomParticipant roomParticipant : roomParticipants) {
                Participant participant = participantService.getById(roomParticipant.getParticipantId());
                System.out.println(participant);
                //roomParticipant.setParticipant(participant);
                room.getParticipants().add(participant);
            }
            //room.setParticipants(roomParticipants);
            System.out.println(room.getParticipants());
        }

        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        roomService.delete(id);
        return "redirect:/room/list";
    }



}
