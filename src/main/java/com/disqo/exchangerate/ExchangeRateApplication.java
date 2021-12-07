package com.disqo.exchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ExchangeRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateApplication.class, args);
    }

}