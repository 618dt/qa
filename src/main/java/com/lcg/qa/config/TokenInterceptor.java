package com.lcg.qa.config;

import com.alibaba.fastjson.JSON;
import com.lcg.qa.model.result.Result;
import com.lcg.qa.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        log.info("获取到的token为{}", token);
        if(token != null){
            //解析token
            Long id = JwtHelper.getUserId(token);
            log.info("解析到的用户id为{}", id);
            if (!StringUtils.isEmpty(id)) {
                //通过校验
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try{
            Result r = Result.error();
            response.getWriter().append(JSON.toJSONString(r));
            log.info("认证失败，未通过拦截器");
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }
}