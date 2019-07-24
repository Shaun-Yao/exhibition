package com.honji.exhibition.controller;


import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.entity.Room;
import com.honji.exhibition.service.IParticipantService;
import com.honji.exhibition.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/toAdd")
    public String toAdd(Model model) {
        List<Participant> participants = participantService.getByArea(11l);
        model.addAttribute("participants", participants);
        return "roomForm";
    }

    @GetMapping("/toEdit")
    public String toEdit(@RequestParam Long id, Model model) {
        Room room = roomService.getById(id);
        List<Participant> participants = participantService.getByArea(11l);
        model.addAttribute("participants", participants);
        model.addAttribute("room", room);
        return "roomForm";
    }
}
