package com.honji.exhibition.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private HttpServletRequest request;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public void exceptionHandler(Exception ex) {
        log.error("请求{}捕获到异常 {}", request.getRequestURI(), ex);
    }

}
