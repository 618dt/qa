package com.lcg.qa.controller;

import com.lcg.qa.model.result.Result;
import com.lcg.qa.model.vo.LoginParam;
import com.lcg.qa.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
@CrossOrigin
public class CommonController {

    @Autowired
    private CommonService commonService;

    // 权限码请求接口
    @PostMapping("code/request")
    public Result getRequestPermissionCode(@RequestBody String emailJson) {
        return commonService.getRequestPermissionCode(emailJson);
    }

    // 邮箱验证码接口
    @PostMapping("code/email")
    public Result sendEmailCode(@RequestBody LoginParam loginParam) {
        return commonService.sendEmailCode(loginParam);
    }
}
