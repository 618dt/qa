package com.lcg.qa.config;

import com.lcg.qa.core.CoreProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ASUS
 */
@Configuration
public class ConfigBean {

    /** 指定问题question及字典的txt模板所在的根目录*/
    @Value("${rootDirPath}")
    private String rootDirPath;

    @Bean
    public CoreProcessor modelProcess() throws Exception{
        return new CoreProcessor(rootDirPath);
    }
}
