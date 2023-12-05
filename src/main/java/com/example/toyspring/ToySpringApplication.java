package com.example.toyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Configuration
public class ToySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToySpringApplication.class, args);
    }
    

}
