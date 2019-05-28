package com.ayundao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AyundaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AyundaoApplication.class, args);
    }

}
