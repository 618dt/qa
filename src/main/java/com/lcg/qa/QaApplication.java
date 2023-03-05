package com.lcg.qa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lcg"})
@MapperScan("com.lcg.qa.mapper")
public class QaApplication {
	//启动类
	public static void main(String[] args) {
		SpringApplication.run(QaApplication.class, args);
	}

}
