package com.honji.exhibition.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Schedule;
import com.honji.exhibition.entity.ScheduleTimeConfig;
import com.honji.exhibition.entity.Shop;
import com.honji.exhibition.entity.User;
import com.honji.exhibition.service.IScheduleService;
import com.honji.exhibition.service.IScheduleTimeConfigService;
import com.honji.exhibition.service.IShopService;
import com.honji.exhibition.service.IUserService;
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
    private IUserService userService;

    @Autowired
    private IShopService shopService;

    @Autowired
    private IScheduleTimeConfigService scheduleTimeConfigService;

    @Autowired
    private HttpSession session;

    @GetMapping("/toAdd")
    public String toAdd(Model model) {
        Object userId = session.getAttribute("userId");
        User user = userService.getById(userId.toString());
        Shop shop = shopService.getById(user.getShopId());

        QueryWrapper<ScheduleTimeConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_type", shop.getType());
        ScheduleTimeConfig scheduleTimeConfig = scheduleTimeConfigService.getOne(queryWrapper);
        model.addAttribute("scheduleTimeConfig", scheduleTimeConfig);

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
