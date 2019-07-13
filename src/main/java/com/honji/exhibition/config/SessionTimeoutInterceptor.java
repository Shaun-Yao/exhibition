package com.honji.exhibition.config;

import com.honji.exhibition.entity.User;
import com.honji.exhibition.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
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
    private UserMapper userMapper;

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
        if (StringUtils.isEmpty(code) && userId == null) {//session过期需要重新使用微信网页登录授权
            //System.out.println("需要微信授权。。");
            final String url = request.getRequestURL().toString();
            String authUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, null);
            response.sendRedirect(authUrl);
            return false;
        }
        return true;
    }

}
