package com.modzo.ors.stations.services.stream.scrapper.info;

import com.modzo.ors.stations.services.stream.reader.WebPageReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.contains;

@Component
class IcyHeaderStreamScrappingStrategy implements StreamInfoScrappingStrategy {
    @Override
    public Optional<StreamScrapper.Response> extract(WebPageReader.Response data) {
        Set<Map.Entry<String, String>> headers = data.getHeaders().entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey().toLowerCase(), e.getValue()))
                .collect(Collectors.toSet());

        boolean noIcyHeaders = headers.stream()
                .map(Map.Entry::getKey)
                .noneMatch(key -> contains(key, "icy"));

        if (noIcyHeaders) {
            return Optional.empty();
        }

        StreamScrapper.Response.Format format = StreamScrapper.Response.Format.findFormat(
                find(headers, "content-type").orElse(EMPTY)
        );

        Integer bitRate = find(headers, "icy-br")
                .map(Integer::valueOf)
                .orElse(0);

        String streamName = find(headers, "icy-name")
                .orElse(EMPTY);

        List<String> genres = find(headers, "icy-genre")
                .map(List::of)
                .orElse(List.of());

        String website = find(headers, "icy-url")
                .orElse(EMPTY);

        return new StreamScrapper.ResponseBuilder()
                .setListingStatus(EMPTY)
                .setFormat(format)
                .setBitrate(bitRate)
                .setListenerPeak(0)
                .setStreamName(streamName)
                .setGenres(genres)
                .setWebsite(website)
                .buildAsOptional();
    }

    private Optional<String> find(Set<Map.Entry<String, String>> headers, String headerName) {
        return headers.stream()
                .filter(entry -> StringUtils.equals(entry.getKey(), headerName))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
