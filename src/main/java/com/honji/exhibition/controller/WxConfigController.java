package com.honji.exhibition.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/"})
public class WxConfigController {

    //@GetMapping("MP_verify_yROjuqyGsEFtPr0M.txt")
    @GetMapping("MP_verify_9dQkDF12uPmh1y78.txt")
    private String returnConfigFile() {
        //return "yROjuqyGsEFtPr0M";
        return "9dQkDF12uPmh1y78";
    }

}
