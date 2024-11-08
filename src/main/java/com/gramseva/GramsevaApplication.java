package com.gramseva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GramsevaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GramsevaApplication.class,args);
    }

}
