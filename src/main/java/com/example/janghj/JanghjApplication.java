package com.example.janghj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JanghjApplication {

    public static void main(String[] args) {
        SpringApplication.run(JanghjApplication.class, args);
    }

}
