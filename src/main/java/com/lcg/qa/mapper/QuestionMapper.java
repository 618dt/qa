package com.lcg.qa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcg.qa.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 在xml文件自己写sql,根据问题获取对应的答案
     * @param question
     * @return
     */
    Question findAnswer(String question);
}
