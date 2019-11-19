package com.phpsong.user.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: qiuyisong
 * @Date: 2019/11/19 16:12
 * 用户控制器
 */
@RestController
public class UserController {
   /* @Autowired
    private UserService userService;

    *//**
     * 校验数据
     * @param data
     * @param type
     * @return
     *//*
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    *//**
     * 发送验证码
     * @param phone
     * @return
     *//*
    @PostMapping("send")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone) {
        userService.sendVerifyCode(phone);
        return ResponseEntity.noContent().build();
    }

    *//**
     * 用户注册
     * @param user
     * @param code
     * @return
     *//*
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code")String code) {
        userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    *//**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     *//*
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.queryUser(username, password));
    }*/
}
