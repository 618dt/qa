package com.lcg.qa.service;

import com.lcg.qa.model.result.Result;

/**
 * @author LCG
 */
public interface QuestionService {
    /**
     * 根据问题查找答案进行返回
     * @param question
     * @return
     */
    Result findAnswer(String question);
}
