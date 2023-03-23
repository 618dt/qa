package com.lcg.qa.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LCG
 */
@Configuration
@MapperScan("com.lcg.qa.mapper")
public class PageConfig {
    /**
     * 原来的配置过时了,这是新的配置方法
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //之前配置的分页没有生效,是因为忘记配置了数据库类型这一部分
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.getDbType("mysql")));
        return interceptor;
    }
}
