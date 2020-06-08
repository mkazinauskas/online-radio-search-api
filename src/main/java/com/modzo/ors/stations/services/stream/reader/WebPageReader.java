package com.modzo.ors.stations.services.stream.reader;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class WebPageReader {

    private static final Logger log = LoggerFactory.getLogger(WebPageReader.class);

    private final List<UrlReader> urlReaders;

    public WebPageReader(List<UrlReader> urlReaders) {
        this.urlReaders = urlReaders;
    }

    public Optional<WebPageReader.Response> read(String url) {
        log.info(String.format("Loading url = `%s`", url));
        return urlReaders.stream()
                .map(reader -> reader.read(url))
                .filter(Optional::isPresent)
                .findFirst()
                .orElseGet(Optional::empty);
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
                    .map(item -> Map.entry(item.getKey().toLowerCase(), StringUtils.join(item.getValue(), ";")))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        public Optional<String> findHeader(String headerName) {
            return getHeaders().entrySet().stream()
                    .filter(entry -> StringUtils.equalsIgnoreCase(entry.getKey(), headerName))
                    .findFirst()
                    .map(Map.Entry::getValue);
        }

        public Optional<String> getBody() {
            return ofNullable(body);
        }

        public boolean hasAudioContentTypeHeader() {
            return findHeader(HttpHeaders.CONTENT_TYPE)
                    .filter(type -> StringUtils.contains(type, "audio"))
                    .isPresent();
        }
    }
}