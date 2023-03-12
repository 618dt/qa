package com.lcg.qa.service;

public interface FeedbackService {
    /**
     * 提交反馈内容
     * @param userId
     * @param name
     * @param message
     * @return
     */
    boolean submitFeedback(Long userId, String name, String message);
}
