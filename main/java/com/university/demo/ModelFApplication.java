package com.university.demo;

import com.university.demo.util.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({FileProperties.class})
public class ModelFApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelFApplication.class, args);
    }
}
