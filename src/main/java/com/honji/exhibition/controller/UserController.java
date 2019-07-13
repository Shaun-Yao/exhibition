package com.honji.exhibition.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.Participant;
import com.honji.exhibition.entity.User;
import com.honji.exhibition.service.IParticipantService;
import com.honji.exhibition.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
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

        if( userId == null && StringUtils.isNotBlank(code)) {//code非空则是微信网页登录跳转过来的
            try {

                WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                final String openId = wxMpUser.getOpenId();
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("open_id", openId);
                user = userService.getOne(queryWrapper);
                session.setAttribute("openId", openId);
                //model.addAttribute("openId", openId);
                if(user != null) {
                    model.addAttribute("user", user);
                    session.setAttribute("userId", user.getId());
                }
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }

        if (user != null) {
            QueryWrapper<Participant> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user.getId());
            List<Participant> participants = participantService.list(queryWrapper);
            model.addAttribute("participants", participants);
        }

        return "applyForm";
    }

/*

    @GetMapping("/toApply")
    public String toApply() {
        //this.initSession(code);
        //session.setAttribute("userId", "sdgfdhgdfhert");
        return "applyForm";
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