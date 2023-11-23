package com.niaobulashi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.niaobulashi.microservices.sentinel.mapper")
public class JavademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavademoApplication.class, args);
    }

}
