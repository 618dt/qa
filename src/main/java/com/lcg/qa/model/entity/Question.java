package com.lcg.qa.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@TableName("sys_qa")
public class Question {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String question;

    private String answer;

    private int type;

    private Long provider;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 其他参数,用于前端展示的数据,以及多表联查获取的数据
     */
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();
}
