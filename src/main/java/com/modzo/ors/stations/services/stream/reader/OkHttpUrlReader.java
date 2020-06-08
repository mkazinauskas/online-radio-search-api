package com.modzo.ors.stations.services.stream.reader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class OkHttpUrlReader implements UrlReader {

    private static final Logger log = LoggerFactory.getLogger(OkHttpUrlReader.class);

    private static final Set<MediaType> ALLOWED_MEDIA_TYPES = Set.of(
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML,
            MediaType.TEXT_HTML,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_XHTML_XML
    );

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public Optional<WebPageReader.Response> read(String url) {

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36")
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!isSuccessfulConnection(response)) {
                return Optional.empty();
            }
            if (!isAllowedContentType(response)) {
                return Optional.of(new WebPageReader.Response(url, response.headers().toMultimap()));
            }

            return Optional.of(
                    new WebPageReader.Response(
                            url,
                            response.headers().toMultimap(),
                            response.body().string()
                    )
            );
        } catch (Exception ex) {
            log.error("Failed to get response");
            return Optional.empty();
        }
    }

    private boolean isAllowedContentType(Response response) {
        Optional<String> contentType = Stream.of(
                response.headers().get(CONTENT_TYPE),
                response.headers().get(CONTENT_TYPE.toLowerCase())
        )
                .filter(Objects::nonNull)
                .findFirst();

        return contentType.map(MediaType::valueOf)
                .filter(type -> ALLOWED_MEDIA_TYPES.stream().anyMatch(type::isCompatibleWith))
                .isPresent();
    }

    private boolean isSuccessfulConnection(Response response) {
        int responseCode = response.code();
        HttpStatus status = HttpStatus.resolve(responseCode);
        return status != null && status.is2xxSuccessful();
    }
}
