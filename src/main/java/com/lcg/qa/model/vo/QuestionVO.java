package com.lcg.qa.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author LCG
 * 管理员前端展示问题的字段
 */
@Data
public class QuestionVO {
    /**
     * 问答对id
     */
    private Long id;

    /**
     * 问题
     */
    private String question;

    /**
     * 答案
     */
    private String answer;

    /**
     * 问答状态 0-未回答, 1-已回答
     */
    private Integer status;
    /**
     * 提供人
     */
    private String name;

    /**
     * 提供人联系方式
     */

    private String email;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
