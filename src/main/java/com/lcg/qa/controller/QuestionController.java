package com.lcg.qa.controller;

import com.lcg.qa.model.result.Result;
import com.lcg.qa.service.FeedbackService;
import com.lcg.qa.service.QuestionService;
import com.lcg.qa.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.lcg.qa.constant.HttpStatusEnum.INSERT_ERROR;

/**
 * @author LCG
 */

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private FeedbackService feedbackService;
    /**
     * 根据问题查询返回答案
     *
     * @param question
     * @return
     */
    @GetMapping("/findAnswer")
    public Result findAnswer(String question) {
        return questionService.findAnswer(question);
    }


    /**
     * 用户提交问题
     *
     * @param question 问题
     * @param userId       用户id
     * @return
     */
    @PostMapping("/submitQuestion")
    public Result submitQuestion(@RequestParam("question") String question, @RequestParam("id") Long userId) {
        boolean res = questionService.submitQuestion(question, userId);
        if (res) {
            return Result.ok();
        } else {
            return Result.error(INSERT_ERROR);
        }
    }

    @PostMapping("/submitFeedback")
    public Result submitFeedback(String message, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            Long userId = JwtHelper.getUserId(token);
            String name = JwtHelper.getUserName(token);
            boolean res = feedbackService.submitFeedback(userId, name, message);
            if (res) {
                return Result.ok();
            }
        }
        return Result.error(INSERT_ERROR);
    }
}
