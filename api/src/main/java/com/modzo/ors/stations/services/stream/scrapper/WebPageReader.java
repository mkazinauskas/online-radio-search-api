package com.modzo.ors.stations.services.stream.scrapper;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Optional<Response> read(String url) {
        HttpURLConnection connection = null;
        try {
            URL parsedUrl = new URL(url);
            connection = (HttpURLConnection) parsedUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
                    + "(KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
            connection.setInstanceFollowRedirects(false);

            if (!isSuccessfulConnection(connection)) {
                log.error(String.format("Failed to establish connection to url = `%s`", url));
                return Optional.empty();
            }

            Map<String, List<String>> headerFields = connection.getHeaderFields();
            if (!contentTypeIsAllowed(headerFields)) {
                log.error(String.format("Url = `%s` is not acceptable content type", url));
                return Optional.of(new Response(headerFields));
            }

            String body = new String(((InputStream) connection.getContent()).readAllBytes(), StandardCharsets.UTF_8);
            return Optional.of(new Response(headerFields, body));
        } catch (Exception exception) {
            log.error(String.format("Failed to establish connection to url = `%s`", url), exception);
            return Optional.empty();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private boolean contentTypeIsAllowed(Map<String, List<String>> headers) {
        return Optional.ofNullable(headers.get(HttpHeaders.CONTENT_TYPE))
                .filter(CollectionUtils::isNotEmpty)
                .map(list -> list.get(0))
                .map(MediaType::valueOf)
                .filter(type -> ALLOWED_MEDIA_TYPES.stream().anyMatch(type::isCompatibleWith))
                .isPresent();
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

    public static class Response {

        private final Map<String, List<String>> headers;

        private final String body;

        public Response(Map<String, List<String>> headers, String body) {
            this.headers = headers;
            this.body = body;
        }

        public Response(Map<String, List<String>> headers) {
            this.headers = headers;
            this.body = null;
        }

        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        public Map<String, String> getHeadersAsSingleValueMap() {
            return this.headers.entrySet().stream()
                    .filter(item -> StringUtils.isNotEmpty(item.getKey()))
                    .filter(item -> CollectionUtils.isNotEmpty(item.getValue()))
                    .map(item -> Map.entry(item.getKey(), item.getValue().get(0)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        public Optional<String> getBody() {
            return Optional.ofNullable(body);
        }
    }
}