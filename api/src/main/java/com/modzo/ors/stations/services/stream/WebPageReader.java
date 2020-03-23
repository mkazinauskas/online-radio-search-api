package com.modzo.ors.stations.services.stream;

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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;

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
        log.info(String.format("Loading url = `%s`", url));
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
            Optional<String> contentType = Stream.of(
                    headerFields.get(HttpHeaders.CONTENT_TYPE.toLowerCase()),
                    headerFields.get(HttpHeaders.CONTENT_TYPE)
            )
                    .filter(Objects::nonNull)
                    .map(list -> list.get(0))
                    .findFirst();

            boolean contentTypeAllowed = contentType.map(MediaType::valueOf)
                    .filter(type -> ALLOWED_MEDIA_TYPES.stream().anyMatch(type::isCompatibleWith))
                    .isPresent();
            if (!contentTypeAllowed) {
                log.error(
                        String.format("Url = `%s` is not acceptable content type = `%s`", url, contentType.orElse(EMPTY))
                );
                return Optional.of(new Response(url, headerFields));
            }

            String body = new String(((InputStream) connection.getContent()).readAllBytes(), StandardCharsets.UTF_8);
            return Optional.of(new Response(url, headerFields, body));
        } catch (Exception exception) {
            log.error(String.format("Failed to establish connection to url = `%s`", url), exception);
            return Optional.empty();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
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

        private final String url;

        private final Map<String, List<String>> headers;

        private final String body;

        public Response(String url, Map<String, List<String>> headers, String body) {
            this.url = url;
            this.headers = headers;
            this.body = body;
        }

        public Response(String url, Map<String, List<String>> headers) {
            this.url = url;
            this.headers = headers;
            this.body = null;
        }

        public String getUrl() {
            return url;
        }

        public Map<String, String> getHeaders() {
            if (Objects.isNull(this.headers)) {
                return Map.of();
            }
            return this.headers.entrySet()
                    .stream()
                    .filter(item -> StringUtils.isNotEmpty(item.getKey()))
                    .filter(item -> CollectionUtils.isNotEmpty(item.getValue()))
                    .map(item -> Map.entry(item.getKey(), item.getValue().get(0)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        public Optional<String> getBody() {
            return ofNullable(body);
        }
    }
}