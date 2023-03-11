package com.lcg.qa.mapper;

import com.lcg.qa.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper {
    Question findAnswer(String question);
}
