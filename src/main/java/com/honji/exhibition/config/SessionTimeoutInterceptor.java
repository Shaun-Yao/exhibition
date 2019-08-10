package com.honji.exhibition.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honji.exhibition.entity.User;
import com.honji.exhibition.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {

        //微信业务域名验证直接通过
        if("/MP_verify_yROjuqyGsEFtPr0M.txt".equals(request.getRequestURI())) {
            return true;
        }

        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        Object userId = session.getAttribute("userId");
        Object wxOpenId = session.getAttribute("openId");

        if (userId != null || wxOpenId != null) {
            //TODO 校验用户是否存在？
            return true;
        }

        if (StringUtils.isEmpty(code)) {//session过期需要重新使用微信网页登录授权
            String url = request.getRequestURL().toString();
            String busId = request.getParameter("busId");
            if (StringUtils.isNotEmpty(busId)) {
                url = url.concat("?busId=").concat(busId);
            }
            System.out.println(url);
            String authUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
            response.sendRedirect(authUrl);
            //return false;
        } else { //回调带有code参数需要校验
            try {
                WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                final String openId = wxMpUser.getOpenId();
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("open_id", openId);
                User user = userService.getOne(queryWrapper);
                session.setAttribute("openId", openId);
                //model.addAttribute("openId", openId);
                if(user != null) {
                    //model.addAttribute("user", user);
                    session.setAttribute("userId", user.getId());
                }
                return true;
            } catch (WxErrorException e) {
                log.error("微信网页授权异常", e);
            }
        }

        return false;
    }

}
