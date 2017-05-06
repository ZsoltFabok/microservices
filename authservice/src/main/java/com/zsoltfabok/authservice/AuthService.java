package com.zsoltfabok.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class AuthService {
    public static void main(String[] args) {
        SpringApplication.run(AuthService.class, args);
    }
}