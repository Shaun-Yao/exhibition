package com.honji.exhibition.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Bus;
import com.honji.exhibition.entity.CheckIn;
import com.honji.exhibition.entity.Shop;
import com.honji.exhibition.service.IBusService;
import com.honji.exhibition.service.ICheckInService;
import com.honji.exhibition.service.IShopService;
import com.honji.exhibition.service.IUserService;
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
 * @since 2019-08-10
 */
@Controller
@RequestMapping("/check-in")
public class CheckInController {

    @Autowired
    private HttpSession session;

    @Autowired
    private IUserService userService;

    @Autowired
    private IShopService shopService;

    @Autowired
    private IBusService busService;

    @Autowired
    private ICheckInService checkInService;

    @GetMapping("/toAdd")
    public String toAdd(@RequestParam Long busId, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        QueryWrapper<CheckIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        CheckIn checkIn = checkInService.getOne(queryWrapper);
        if (checkIn != null) {
            return "checkInSucceed";
        }

        Shop shop = shopService.getByUserId(userId);
        Bus bus = busService.getById(busId);
        model.addAttribute("shop", shop);
        model.addAttribute("bus", bus);

        return "checkInForm";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute CheckIn checkIn) {
        Long userId = (Long) session.getAttribute("userId");
        checkIn.setUserId(userId);
        checkInService.save(checkIn);
        return "checkInSucceed";
    }

}
