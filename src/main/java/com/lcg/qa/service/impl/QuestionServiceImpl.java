package com.lcg.qa.service.impl;

import com.lcg.qa.mapper.QuestionMapper;
import com.lcg.qa.model.entity.Question;
import com.lcg.qa.model.result.Result;
import com.lcg.qa.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public Result findAnswer(String question) {
        //直接查询数据库
        Question res = Optional.ofNullable(questionMapper.findAnswer(question)).orElse(new Question());
        log.info("查询返回结果{}",res);
        if (StringUtils.isEmpty(res.getQuestion())) {
            //问题为空，不存在该问题
            return Result.data("answer","no question");
        } else if (StringUtils.isEmpty(res.getAnswer())&&res.getType()==0) {
            //问题不为空，但答案为空
            return Result.data("answer","no answer");
        } else if (!StringUtils.isEmpty(res.getAnswer())&&res.getType()==1) {
            log.info("answer:{}", res.getAnswer());
            return Result.data("answer", res.getAnswer());
        }
        return Result.error();
    }

    @Override
    public boolean submitQuestion(String question, Long userId) {
        //插入用户
        Question questionDo = new Question();
        int num = 0;
        if (!StringUtils.isEmpty(question) && !StringUtils.isEmpty(userId)) {
            questionDo.setQuestion(question);
            questionDo.setProvider(userId);
            //刚提交,问题未回答
            questionDo.setType(0);
            //新增录入问题时,需要设置创建时间
            questionDo.setCreateTime(new Date());
            try {
                num = questionMapper.insert(questionDo);
                log.info("num = {}",num);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num == 1;
    }
}
