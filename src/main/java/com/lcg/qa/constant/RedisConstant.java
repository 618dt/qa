package com.lcg.qa.constant;

/**
 * @author LCG
 */
public interface RedisConstant {

    // Key
    // 邮箱缓存前缀
    String EMAIL = "EMAIL_";
    // 邮箱请求的权限码
    String EMAIL_REQUEST_VERIFY = "EMAIL_REQUEST_VERIFY_";

    // 缓存时间
    // 10s
    int EXPIRE_TEN_SECOND = 10;
    // 1分钟
    int EXPIRE_ONE_MINUTE = 60;
    // （五分钟）
    int EXPIRE_FIVE_MINUTE = 5 * 60;
    // 半小时（30分钟）
    int EXPIRE_HALF_HOUR = 30 * 60;
    // （1天）
    int EXPIRE_ONE_DAY = 24 * 60 * 60;
}
