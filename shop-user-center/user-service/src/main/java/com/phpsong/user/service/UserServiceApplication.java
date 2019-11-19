package com.phpsong.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: qiuyisong
 * @Date: 2019/11/19 11:01
 */
@SpringBootApplication
@MapperScan(basePackages = "com.phpsong.use.service.dao")
public class UserServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

