package com.lcg.qa.model.result;

import com.lcg.qa.constant.HttpStatusEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    // 把构造方法私有化
    private Result() {}

    // 成功静态方法
    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }


    // 失败静态方法
    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(20001);
        r.setMessage("失败");
        return r;
    }

    public static Result data(String key, Object value){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        Map<String, Object> d = new HashMap<>();
        d.put(key, value);
        r.setData(d);
        return r;
    }

    // 失败静态方法
    public static Result error(HttpStatusEnum httpStatus) {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(httpStatus.getCode());
        r.setMessage(httpStatus.getMsg());
        return r;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

/*    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }*/

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
