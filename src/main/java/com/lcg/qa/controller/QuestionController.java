package com.lcg.qa.controller;

import com.lcg.qa.model.result.Result;
import com.lcg.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LCG
 */

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    //返回答案
    @GetMapping("/findAnswer")
    public Result findAnswer(String question) {
        return questionService.findAnswer(question);
    }
}
