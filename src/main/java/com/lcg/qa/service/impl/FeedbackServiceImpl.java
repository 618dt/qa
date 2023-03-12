package com.lcg.qa.service.impl;

import com.lcg.qa.mapper.FeedbackMapper;
import com.lcg.qa.model.entity.Feedback;
import com.lcg.qa.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    public boolean submitFeedback(Long userId, String name, String message) {
        //向数据库中插入内容
        Feedback feedback = new Feedback();
        int num = 0;
        if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(name) && !StringUtils.isEmpty(message)) {
            feedback.setUserId(userId);
            feedback.setUserName(name);
            feedback.setMessage(message);
            feedback.setCreateTime(new Date());
            try {
                num = feedbackMapper.insert(feedback);
                log.info("num = {}",num);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num==1;
    }
}
