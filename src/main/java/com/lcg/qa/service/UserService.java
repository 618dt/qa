package com.lcg.qa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcg.qa.model.entity.User;
import com.lcg.qa.model.result.Result;
import com.lcg.qa.model.vo.LoginParam;
import com.lcg.qa.model.vo.RegisterParam;

public interface UserService extends IService<User> {

    /**
     * 登录
     * @param loginParam (邮箱和密码)
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 注册
     * @param registerParam (邮箱、密码、确认密码、验证码)
     * @return
     */
    Result register(RegisterParam registerParam);

    /**
     * 找回密码
     * @param loginParam (邮箱、密码、验证码)
     * @return
     */
    Result findPassword(LoginParam loginParam);
}
