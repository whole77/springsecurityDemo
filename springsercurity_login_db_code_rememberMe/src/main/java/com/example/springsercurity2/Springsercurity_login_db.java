package com.example.springsercurity2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.springsercurity2.dao")
public class Springsercurity_login_db {

    public static void main(String[] args) {
        SpringApplication.run(Springsercurity_login_db.class, args);
    }

}
