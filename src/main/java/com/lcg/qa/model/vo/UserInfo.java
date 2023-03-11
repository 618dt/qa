package com.lcg.qa.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserInfo {
    private Long id;
    private String name;
    private String email;
    private String token;
}
