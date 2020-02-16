package com.modzo.ors.services.scrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Component
public class WebPageReader {

    private static final Logger log = LoggerFactory.getLogger(WebPageReader.class);

    private static final Set<MediaType> ALLOWED_MEDIA_TYPES = Set.of(
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML,
            MediaType.TEXT_HTML,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_XHTML_XML
    );

    private final RestTemplate restTemplate;

    WebPageReader(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    public Optional<String> read(String url) {
        try {
            log.info("Reading url = `{}`", url);
            HttpHeaders httpHeaders = restTemplate.headForHeaders(url, String.class);
            MediaType contentType = httpHeaders.getContentType();
            if (contentType == null) {
                log.info("Content type of url = `{}` was null", url);
                return Optional.empty();
            }
            if (ALLOWED_MEDIA_TYPES.stream().anyMatch(contentType::isCompatibleWith)) {
                log.info("Successfully finished reading url = `{}`", url);
                ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
                return Optional.ofNullable(forEntity.getBody());
            } else {
                log.info("Content type `{}` is not readable of url = `{}`", contentType.getType(), url);
                return Optional.empty();
            }
        } catch (Exception exception) {
            log.error(String.format("Failed to read url = `%s`", url), exception);
            return Optional.empty();
        }
    }
}