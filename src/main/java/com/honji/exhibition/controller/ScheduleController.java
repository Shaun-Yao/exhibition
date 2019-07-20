package com.honji.exhibition.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/toAdd")
    public String toAdd() {
        return "scheduleForm";
    }

}
