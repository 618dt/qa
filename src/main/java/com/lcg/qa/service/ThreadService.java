package com.lcg.qa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThreadService {

    @Autowired
    private MailService mailService;

    /**
     * 发送邮箱
     * @param to 收件人
     * @param theme 主题
     * @param content 内容
     */
    @Async("taskExecutor")
    public void sendSimpleMail(String to, String theme, String content) {
        log.info("已发送邮件:主题: {},内容: {}",theme,content);
        //注释掉发送邮件的部分
        mailService.sendSimpleMail(to, theme, content);
    }
}
