package com.lcg.qa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcg.qa.model.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author LCG
 */
@Mapper
@Repository
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
