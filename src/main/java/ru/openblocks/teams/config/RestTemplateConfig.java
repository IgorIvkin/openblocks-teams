package ru.openblocks.teams.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(30000))
                .setReadTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Bean(name = "infrastructure-rest-template")
    public RestTemplate infrastuctureRestTemplate(RestTemplateBuilder builder) {
        return restTemplate(builder);
    }

    @Bean(name = "cs-users-rest-template")
    public RestTemplate csUsersRestTemplate(RestTemplateBuilder builder) {
        return restTemplate(builder);
    }
}
