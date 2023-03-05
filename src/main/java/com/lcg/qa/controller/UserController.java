package com.lcg.qa.controller;

import com.lcg.qa.model.result.Result;
import com.lcg.qa.model.vo.LoginParam;
import com.lcg.qa.model.vo.RegisterParam;
import com.lcg.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // 登录
    @PostMapping("login")
    public Result login(@RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }

    // 注册
    @PostMapping("register")
    public Result register(@RequestBody RegisterParam registerParam) {
        return userService.register(registerParam);
    }

    // 找回密码
    @PostMapping("findPassword")
    public Result findPassword(@RequestBody LoginParam loginParam) {
        return userService.findPassword(loginParam);
    }
}
