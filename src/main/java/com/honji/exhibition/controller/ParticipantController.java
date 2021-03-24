package com.honji.exhibition.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.entity.RoomParticipant;
import com.honji.exhibition.entity.Schedule;
import com.honji.exhibition.entity.ScheduleTimeConfig;
import com.honji.exhibition.model.UserSessionVO;
import com.honji.exhibition.service.IParticipantService;
import com.honji.exhibition.service.IRoomParticipantService;
import com.honji.exhibition.service.IScheduleService;
import com.honji.exhibition.service.IScheduleTimeConfigService;
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
    private IScheduleService scheduleService;

    @Autowired
    private IRoomParticipantService roomParticipantService;

    @Autowired
    private IScheduleTimeConfigService scheduleTimeConfigService;

    @Autowired
    private HttpSession session;

    @GetMapping("/list")
    public String list(Model model) {
        UserSessionVO user = (UserSessionVO) session.getAttribute("user");
        QueryWrapper<Participant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        List<Participant> participants = participantService.list(queryWrapper);

        QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
        scheduleQueryWrapper.eq("user_id", user.getId());
        Schedule schedule = scheduleService.getOne(scheduleQueryWrapper);
        model.addAttribute("schedule", schedule);
        model.addAttribute("participants", participants);
        return "participants";
    }

    @GetMapping("/toAdd")
    public String toAdd(Model model) {
        UserSessionVO user = (UserSessionVO) session.getAttribute("user");
        QueryWrapper<ScheduleTimeConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_type", user.getShopType());
        ScheduleTimeConfig scheduleTimeConfig = scheduleTimeConfigService.getOne(queryWrapper);
        model.addAttribute("timeConfig", scheduleTimeConfig);
        return "participantForm";
    }

    @GetMapping("/toEdit")
    public String toEdit(@RequestParam Long id, Model model) {
        UserSessionVO user = (UserSessionVO) session.getAttribute("user");
        QueryWrapper<ScheduleTimeConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_type", user.getShopType());
        ScheduleTimeConfig scheduleTimeConfig = scheduleTimeConfigService.getOne(queryWrapper);
        model.addAttribute("timeConfig", scheduleTimeConfig);
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

    @ResponseBody
    @PostMapping("/delete")
    public boolean delete(@RequestParam Long id) {
        QueryWrapper<RoomParticipant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("participant_id", id);
        RoomParticipant roomParticipant = roomParticipantService.getOne(queryWrapper);
        if (roomParticipant != null) {//已经被选入同住人不能删除
            return false;
        }
        return participantService.removeById(id);
    }


}
