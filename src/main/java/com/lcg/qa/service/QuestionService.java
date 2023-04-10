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

    /**
     * 从知识图谱中获取答案，通过分词、模板匹配
     * @param question
     * @return
     * @throws Exception
     */
    String getAnswerForNeo4j(String question) throws Exception;

    /**
     * 提交问题
     * @param question
     * @param userId
     * @return
     */
    boolean submitQuestion(String question ,Long userId);

}
