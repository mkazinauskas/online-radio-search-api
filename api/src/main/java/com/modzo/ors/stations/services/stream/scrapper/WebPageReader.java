package com.modzo.ors.stations.services.stream.scrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
        log.info("Reading url = `{}`", url);
        if (!isExpectedPage(url)) {
            return Optional.empty();
        }

        try {
            ResponseEntity<String> forEntity = restTemplate
                    .exchange(url, HttpMethod.GET, DEFAULT_REQUEST_HEADERS, String.class);

            return Optional.ofNullable(forEntity.getBody());
        } catch (Exception exception) {
            log.error(String.format("Failed to read url = `%s`", url), exception);
            return Optional.empty();
        }
    }

    private boolean isExpectedPage(String url) {
        HttpURLConnection connection = null;
        try {
            URL parsedUrl = new URL(url);
            connection = (HttpURLConnection) parsedUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
                    + "(KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
            connection.setInstanceFollowRedirects(false);

            if (!isSuccessfulConnection(connection)) {
                log.error(String.format("Failed to establish connection to url = `%s`", url));
                return false;
            }

            if (!contentTypeIsAllowed(connection)) {
                log.error(String.format("Url = `%s` is not acceptable content type", url));
                return false;
            }

            return true;
        } catch (Exception exception) {
            log.error(String.format("Failed to establish connection to url = `%s`", url), exception);
            return true;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private boolean contentTypeIsAllowed(HttpURLConnection connection) {
        String contentType = connection.getHeaderField(HttpHeaders.CONTENT_TYPE);
        MediaType mediaType = MediaType.valueOf(contentType);
        return ALLOWED_MEDIA_TYPES.stream().anyMatch(mediaType::isCompatibleWith);
    }

    private boolean isSuccessfulConnection(HttpURLConnection connection) {
        try {
            int responseCode = connection.getResponseCode();
            HttpStatus status = HttpStatus.resolve(responseCode);
            return status != null && status.is2xxSuccessful();
        } catch (IOException exception) {
            log.error(
                    String.format("Failed to get response code from url = `%s`", connection.getURL().toString()),
                    exception
            );
            return false;
        }
    }

    private static HttpEntity<String> headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
                + "(KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
        return new HttpEntity<>(headers);
    }
}