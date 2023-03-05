package com.lcg.qa.model.vo;

import lombok.Data;

@Data
public class RegisterParam {
    // 用户名
    private String name;
    // 邮箱
    private String email;
    // 密码
    private String password;
    // 确认密码
    private String passwordConfirm;
    // 验证码
    private String code;
}
