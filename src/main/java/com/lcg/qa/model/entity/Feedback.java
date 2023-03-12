package com.lcg.qa.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author ASUS
 */
@Data
@TableName("feedback")
public class Feedback {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String message;

    @TableField("user_name")
    private String userName;

    @TableField("user_id")
    Long userId;

    /**
     * 前端展示的日期格式
     * value:数据库对应的字段名
     * fill: 填充插入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

}
