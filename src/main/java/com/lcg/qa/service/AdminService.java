package com.lcg.qa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcg.qa.model.entity.Question;
import com.lcg.qa.model.result.Result;
import com.lcg.qa.model.vo.AdminParam;


/**
 * @author LCG
 */
public interface AdminService {
    /**
     * 管理员登录方法
     * @return
     */
    Result loginAdmin(AdminParam adminParam);


    /**
     * 分页查询
     * @param pageParam 分页参数
     * @param type 查询条件
     * @return
     */
    IPage<Question> selectPage(Page<Question> pageParam,Integer type);

    /**
     * 新增问答对
     * @param question
     * @return
     */
    boolean addQuestion(Question question);

    /**
     * 修改问答对
     * @param question
     * @return
     */
    boolean updateQuestion(Question question);

    /**
     * 删除问答对
     * @param id
     * @return
     */
    boolean deleteQuestion(Long id);

    /**
     * 根据id查询用户详情
     * @param id
     * @return
     */
    Question getQuestionById(Long id);


}
