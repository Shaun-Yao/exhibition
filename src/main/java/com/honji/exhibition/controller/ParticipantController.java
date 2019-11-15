package com.honji.exhibition.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.model.UserSessionVO;
import com.honji.exhibition.service.IParticipantService;
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
 * @since 2019-07-07
 */
@Controller
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private IParticipantService participantService;

    @Autowired
    private HttpSession session;

    @GetMapping("/list")
    public String list(Model model) {
        UserSessionVO user = (UserSessionVO) session.getAttribute("user");
        QueryWrapper<Participant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        List<Participant> participants = participantService.list(queryWrapper);

        model.addAttribute("participants", participants);
        return "participants";
    }

    @GetMapping("/toAdd")
    public String toAdd() {
        return "participantForm";
    }

    @GetMapping("/toEdit")
    public String toEdit(@RequestParam Long id, Model model) {
        Participant participant = participantService.getById(id);
        model.addAttribute("participant", participant);
        return "participantForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Participant participant) {
        UserSessionVO user = (UserSessionVO) session.getAttribute("user");
        participant.setUserId(user.getId());
        participantService.saveOrUpdate(participant);
        return "redirect:/participant/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String id, Model model) {
        //Participant participant = participantService.getById(id);
        participantService.removeById(id);
        return "redirect:/participant/list";
    }


}
