package com.lcg.qa.service;

import com.lcg.qa.model.result.Result;
import com.lcg.qa.model.vo.LoginParam;

public interface CommonService {

    /**
     * 获取请求权限码
     * @param emailJson 邮箱
     * @return
     */
    Result getRequestPermissionCode(String emailJson);

    /**
     * 发送邮箱验证码
     * @param loginParam （邮箱和权限码）
     * @return
     */
    Result sendEmailCode(LoginParam loginParam);
}
