package com.honji.exhibition.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Autowired
    private SessionTimeoutInterceptor sessionTimeoutInterceptor;
/*

    @Bean
    public SessionTimeoutInterceptor sessionTimeoutInterceptor() {
        return new SessionTimeoutInterceptor();
    }
*/



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(sessionTimeoutInterceptor).excludePathPatterns("/webjars/**");
    }

}
