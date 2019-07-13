package com.honji.exhibition;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.honji.exhibition.mapper")
public class ExhibitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExhibitionApplication.class, args);
    }

}
