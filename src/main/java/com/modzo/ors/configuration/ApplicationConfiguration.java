package com.modzo.ors.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ApplicationConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
