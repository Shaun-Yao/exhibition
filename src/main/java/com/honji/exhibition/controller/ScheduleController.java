package com.honji.exhibition.controller;


import com.honji.exhibition.entity.Schedule;
import com.honji.exhibition.service.IScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yao
 * @since 2019-07-20
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private HttpSession session;

    @GetMapping("/toAdd")
    public String toAdd() {
        return "scheduleForm";
    }


    @GetMapping("/test")
    public String test() {
        return "scheduleFormTest";
    }


    @GetMapping("/toEdit")
    public String toEdit(@RequestParam Long id, Model model) {
        Schedule schedule = scheduleService.getById(id);
        model.addAttribute("schedule", schedule);
        return "scheduleForm";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Schedule schedule) {
        System.out.println(schedule.getArrivedTime());
        System.out.println(schedule.getLeavedTime());
        String arrivedNum = schedule.getArrivedNum();
        String leavedNum = schedule.getLeavedNum();
        if (StringUtils.isNotEmpty(arrivedNum)) {
            schedule.setArrivedNum(arrivedNum.toUpperCase());
        }
        if (StringUtils.isNotEmpty(leavedNum)) {
            schedule.setLeavedNum(leavedNum.toUpperCase());
        }
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/user/toApply";
    }


}
