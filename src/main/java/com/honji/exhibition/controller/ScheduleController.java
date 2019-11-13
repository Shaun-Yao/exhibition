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
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getById(userId);
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
    public String add(@ModelAttribute Schedule schedule, @RequestParam("file") MultipartFile[] files) {

        System.out.println(files.length);
        String arrivedNum = schedule.getArrivedNum();
        String leavedNum = schedule.getLeavedNum();
        if (StringUtils.isNotEmpty(arrivedNum)) {
            schedule.setArrivedNum(arrivedNum.toUpperCase());
        }
        if (StringUtils.isNotEmpty(leavedNum)) {
            schedule.setLeavedNum(leavedNum.toUpperCase());
        }

        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }

        /*if (!file.isEmpty()) {
            System.out.println(file.getName());
            try {
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                final String originalFileName = file.getOriginalFilename();
                final String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
                Path path = Paths.get(uploadPath + "22" + fileType );
                //System.out.println(path.);
                Files.write(path, bytes);
                log.info("上传行程图片成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
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
