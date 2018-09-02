package com.mozdzo.ors.services.scrapper.stream;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
class WebPageReader {
    private final RestTemplate restTemplate;

    WebPageReader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    Optional<String> read(String url) {
        try {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            return Optional.ofNullable(forEntity.getBody());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
