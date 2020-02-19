package com.modzo.ors.stations.services.stream.scrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    private static final HttpEntity<String> DEFAULT_REQUEST_HEADERS = headers();

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
            HttpHeaders httpHeaders = restTemplate
                    .exchange(url, HttpMethod.HEAD, DEFAULT_REQUEST_HEADERS, String.class)
                    .getHeaders();

            MediaType contentType = httpHeaders.getContentType();
            if (contentType == null) {
                log.info("Content type of url = `{}` was null", url);
                return Optional.empty();
            }
            if (ALLOWED_MEDIA_TYPES.stream().anyMatch(contentType::isCompatibleWith)) {
                log.info("Successfully finished reading url = `{}`", url);
                ResponseEntity<String> forEntity = restTemplate
                        .exchange(url, HttpMethod.GET, DEFAULT_REQUEST_HEADERS, String.class);

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

    private static HttpEntity<String> headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
                + "(KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
        return new HttpEntity<>(headers);
    }
}