package com.ayundao;

import com.ayundao.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({
        "com.ayundao.base.*",
        "com.ayundao.entity",
        "com.ayundao.controller",
        "com.ayundao.service",
        "com.ayundao.repository",
})
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class AyundaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AyundaoApplication.class, args);
    }

}
