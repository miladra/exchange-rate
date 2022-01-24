package com.amazing.exchangerate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Exchange Rate API", version = "2.0", description = "Exchange Rate"))
public class ExchangeRateApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateApplication.class, args);
    }
}