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

    @ResponseBody
    @PostMapping("/add")
    public String add(@ModelAttribute Schedule schedule, @RequestParam("files[]") MultipartFile[] files) {

        //Long userId = schedule.getUserId();
        String arrivedNum = schedule.getArrivedNum();
        String leavedNum = schedule.getLeavedNum();
        if (StringUtils.isNotEmpty(arrivedNum)) {
            schedule.setArrivedNum(arrivedNum.toUpperCase());
        }
        if (StringUtils.isNotEmpty(leavedNum)) {
            schedule.setLeavedNum(leavedNum.toUpperCase());
        }


        String[] pictures = new String[2];
        int fileLength = files.length;
        if (files != null && fileLength > 0) {
            for (int i = 0; i < fileLength; i++) {
                try {
                    if (fileLength == 1) {//只有一个文件是

                    }
                    //用户ID加序号拼接成文件名，例 1-1，1-2
                    String fileName = String.valueOf(schedule.getUserId()).concat("-").concat(String.valueOf(i));
                    byte[] bytes = files[i].getBytes();
                    final String originalFileName = files[i].getOriginalFilename();
                    final String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
                    Path path = Paths.get(uploadPath + fileName + fileType );
                    Files.write(path, bytes);
                    log.info("{} 上传行程图片成功", fileName);
                    pictures[i] = fileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (StringUtils.isEmpty(schedule.getPicture1())) {
            schedule.setPicture1(pictures[0]);
        }

        if (StringUtils.isEmpty(schedule.getPicture2())) {
            if (pictures.length > 1) {// 如果有两张图片上传则取第2张

            }
            schedule.setPicture2(pictures[0]);
        }

        if (StringUtils.isNotEmpty(pictures[0])) {
            schedule.setPicture1(pictures[0]);
        }
        if (StringUtils.isNotEmpty(pictures[1])) {
            schedule.setPicture2(pictures[1]);
        }
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/room/toAdd";
    }

    @RequestMapping("show")
    public ResponseEntity showPhotos(){

        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + uploadPath + "22.png"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
