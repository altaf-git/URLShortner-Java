package com.pinboxinclusion.logic.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@EnableAutoConfiguration
@ComponentScan(
        basePackages = {"com.urlShortner"}
)
@SpringBootApplication
@EnableWebFlux
@Configuration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
}
