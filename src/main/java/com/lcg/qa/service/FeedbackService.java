package com.lcg.qa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcg.qa.model.entity.Feedback;

/**
 * @author LCG
 */
public interface FeedbackService {
    /**
     * 提交反馈内容
     * @param userId
     * @param name
     * @param message
     * @return
     */
    boolean submitFeedback(Long userId, String name, String message);

    /**
     * 反馈列表
     * @return
     */
    IPage<Feedback> getPage(Page<Feedback> pageParam, Integer status);

    /**
     * 删除反馈列表
     * @param id
     * @return
     */
    boolean deleteFeedback(Long id);

    boolean updateFeedback(Long id,int status);

}
