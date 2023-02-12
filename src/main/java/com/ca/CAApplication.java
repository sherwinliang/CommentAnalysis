package com.ca;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ca.mapper")
public class CAApplication {

    public static void main(String[] args) {
        SpringApplication.run(CAApplication.class, args);
    }
}
