package com.kaka.SpringBootRocketMQ;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.kaka.SpringBootRocketMQ.mapper")
public class SpringBootRocketMqApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRocketMqApplication.class, args);
	}

}
