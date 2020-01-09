package com.honji.exhibition.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Schedule;
import com.honji.exhibition.entity.ScheduleTimeConfig;
import com.honji.exhibition.model.UserSessionVO;
import com.honji.exhibition.service.IScheduleService;
import com.honji.exhibition.service.IScheduleTimeConfigService;
import com.honji.exhibition.service.IShopService;
import com.honji.exhibition.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yao
 * @since 2019-07-20
 */
@Slf4j
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    private ResourceLoader resourceLoader;

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
        UserSessionVO user = (UserSessionVO) session.getAttribute("user");
        QueryWrapper<ScheduleTimeConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_type", user.getShopType());
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
        UserSessionVO user = (UserSessionVO) session.getAttribute("user");
        QueryWrapper<ScheduleTimeConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_type", user.getShopType());
        ScheduleTimeConfig scheduleTimeConfig = scheduleTimeConfigService.getOne(queryWrapper);
        model.addAttribute("scheduleTimeConfig", scheduleTimeConfig);

        Schedule schedule = scheduleService.getById(id);
        model.addAttribute("schedule", schedule);
        return "scheduleForm";
    }

    //@ResponseBody
    @PostMapping("/save")
    public String save(@ModelAttribute Schedule schedule) {

        //Long userId = schedule.getUserId();
        String arrivedNum = schedule.getArrivedNum();
        String leavedNum = schedule.getLeavedNum();
        if (StringUtils.isNotEmpty(arrivedNum)) {
            schedule.setArrivedNum(arrivedNum.toUpperCase());
        }
        if (StringUtils.isNotEmpty(leavedNum)) {
            schedule.setLeavedNum(leavedNum.toUpperCase());
        }

        scheduleService.saveOrUpdate(schedule);
        return "redirect:/room/toAdd";
    }

    @ResponseBody
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String newFileName = "";
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();//原文件名
                byte[] bytes = file.getBytes();
                int idx = fileName.lastIndexOf(".");
                String suffix= fileName.substring(idx); //文件后缀
                String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
                newFileName = time.concat(suffix);
                String filePath = uploadPath.concat(newFileName);
                Path path = Paths.get(filePath);
                Files.write(path, bytes);
                log.info("{} 上传行程图片成功", fileName);

            } catch (IOException e) {
                log.info("上传行程图片失败");
                e.printStackTrace();
            }
        }
        return newFileName;
    }

    @RequestMapping("show")
    public ResponseEntity showPhotos(@RequestParam String fileName){

        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + uploadPath.concat(fileName)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
