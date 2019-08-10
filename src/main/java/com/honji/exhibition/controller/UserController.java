package com.honji.exhibition.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.entity.Schedule;
import com.honji.exhibition.entity.Shop;
import com.honji.exhibition.entity.User;
import com.honji.exhibition.service.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private WxMpService wxMpService;


    @Autowired
    private IUserService userService;

    @Autowired
    private IParticipantService participantService;

    @Autowired
    private IShopService shopService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private HttpSession session;



    @GetMapping("/toApply")
    public String index(@RequestParam(required = false) String code, Model model) {
        Object userId = session.getAttribute("userId");
        User user = null;
        if( userId != null ) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userId);
            user = userService.getOne(queryWrapper);
            model.addAttribute("user", user);
        }


        if (user != null) {
            Shop shop = shopService.getById(user.getShopId());
            QueryWrapper<Participant> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user.getId());
            List<Participant> participants = participantService.list(queryWrapper);

            QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
            scheduleQueryWrapper.eq("user_id", user.getId());
            Schedule schedule = scheduleService.getOne(scheduleQueryWrapper);

            model.addAttribute("shopCode", shop.getCode());
            model.addAttribute("participants", participants);
            model.addAttribute("schedule", schedule);
        }
        return "applyForm";
    }
/*

    @GetMapping("/toApplySupplement")
    public String toApplySupplement(@RequestParam(required = false) String code, Model model) {
        Object userId = session.getAttribute("userId");
        User user = null;
        if( userId != null ) {
            user = userService.getById((Long) userId);
            model.addAttribute("user", user);
        }

        if (user != null) {
            Shop shop = shopService.getById(user.getShopId());
            QueryWrapper<Participant> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user.getId());
            List<Participant> participants = participantService.list(queryWrapper);

            QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
            scheduleQueryWrapper.eq("user_id", user.getId());

            model.addAttribute("shopCode", shop.getCode());
            model.addAttribute("participants", participants);
        }
        return "applyFormSupplement";
    }
*/


    @GetMapping("/toEdit")
    public String toEdit() {
        //this.initSession(code);
        //session.setAttribute("userId", "sdgfdhgdfhert");
        return "editApplyInfo";
    }


    @PostMapping("/apply")
    public String apply(@ModelAttribute User user) {
        //log.info("openId=={}", user.getOpenId());
        //不确定是哪种情况会导致openId为空，暂时阻止这种情况报名
        if (StringUtils.isEmpty(user.getOpenId())) {
            log.error("opendId 为空");
            return "error";
        }
        Shop shop = shopService.getByCode(user.getShopCode());
        user.setShopId(shop.getId());
        boolean result = userService.saveOrUpdate(user);
        Object userId = session.getAttribute("userId");
        if( userId == null && result ) { //保存成功需要设置session
            session.setAttribute("userId", user.getId());
            return "redirect:/participant/toAdd";//无id 为新增
        }

        return "redirect:/user/toApply";

    }


    /*private void initSession(String code)  {
        Object userId = session.getAttribute("userId");
        if( userId == null && StringUtils.isNotBlank(code)) {//code非空则是微信网页登录跳转过来的
            try {

                WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                final String openId = wxMpUser.getOpenId();
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("open_id", openId);
                User user = userService.getOne(queryWrapper);
                session.setAttribute("openId", openId);
                if(user != null) {
                    session.setAttribute("userId", user.getId());
                }

            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
    }*/
}