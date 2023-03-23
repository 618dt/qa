package com.lcg.qa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author LCG
 */
@Repository
@Mapper
public interface AdminMapper {
    /**
     * 查询密码
     * @param name
     * @return
     */
    String findPasswordByAdmin(String name);


}
