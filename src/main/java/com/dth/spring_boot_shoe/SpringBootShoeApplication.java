package com.dth.spring_boot_shoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootShoeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootShoeApplication.class, args);
    }

}
