package com.elecxa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ElecxaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElecxaApplication.class, args);
    }
} 