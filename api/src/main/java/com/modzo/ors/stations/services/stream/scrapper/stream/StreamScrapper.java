package com.modzo.ors.stations.services.stream.scrapper.stream;

import com.modzo.ors.stations.services.stream.scrapper.WebPageReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Component
public class StreamScrapper {

    private final WebPageReader siteReader;

    private final StreamInfoUrlGenerator generator;

    private final List<StreamInfoScrappingStrategy> streamInfoScrappingStrategies;

    public StreamScrapper(WebPageReader siteReader,
                          StreamInfoUrlGenerator generator,
                          List<StreamInfoScrappingStrategy> streamInfoScrappingStrategies) {
        this.siteReader = siteReader;
        this.generator = generator;
        this.streamInfoScrappingStrategies = streamInfoScrappingStrategies;
    }

    public Optional<Response> scrap(Request request) {
        return generator.generateUrls(request.url)
                .stream()
                .map(this.siteReader::read)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(getResponseHavingHighestPropertyCount()
                ).findFirst()
                .orElse(Optional.empty());
    }

    private Function<WebPageReader.Response, Optional<Response>> getResponseHavingHighestPropertyCount() {
        return response -> streamInfoScrappingStrategies.stream()
                .map(strategy -> strategy.extract(response))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .max(Comparator.comparing(Response::filledPropertyCount));
    }

    public static class Request {

        private final String url;

        public Request(String url) {
            this.url = url;
        }
    }

    public static class Response {
        private final String listingStatus;
        private final Format format;
        private final int bitrate;
        private final int listenerPeak;
        private final String streamName;
        private final List<String> genres;
        private final String website;
        private final int filledPropertyCount;

        private Response(String listingStatus,
                         Format format,
                         int bitrate,
                         int listenerPeak,
                         String streamName,
                         List<String> genres,
                         String website,
                         int filledPropertyCount) {
            this.listingStatus = listingStatus;
            this.format = format;
            this.bitrate = bitrate;
            this.listenerPeak = listenerPeak;
            this.streamName = streamName;
            this.genres = genres;
            this.website = website;
            this.filledPropertyCount = filledPropertyCount;
        }

        public String getListingStatus() {
            return listingStatus;
        }

        public Format getFormat() {
            return format;
        }

        public int getBitrate() {
            return bitrate;
        }

        public int getListenerPeak() {
            return listenerPeak;
        }

        public String getStreamName() {
            return streamName;
        }

        public List<String> getGenres() {
            return genres;
        }

        public String getWebsite() {
            return website;
        }

        public enum Format {
            MP3, AAC, MPEG, UNKNOWN;

            public static Format findFormat(String line) {
                String uppercaseLine = line.toUpperCase();
                for (Format format : values()) {
                    if (uppercaseLine.contains(format.name())) {
                        return format;
                    }
                }
                return UNKNOWN;
            }
        }

        public int filledPropertyCount() {
            return filledPropertyCount;
        }
    }

    public static class ResponseBuilder {
        private String listingStatus;
        private StreamScrapper.Response.Format format;
        private int bitrate;
        private int listenerPeak;
        private String streamName;
        private List<String> genres;
        private String website;

        public ResponseBuilder setListingStatus(String listingStatus) {
            this.listingStatus = listingStatus;
            return this;
        }

        public ResponseBuilder setFormat(StreamScrapper.Response.Format format) {
            this.format = format;
            return this;
        }

        public ResponseBuilder setBitrate(int bitrate) {
            this.bitrate = bitrate;
            return this;
        }

        public ResponseBuilder setListenerPeak(int listenerPeak) {
            this.listenerPeak = listenerPeak;
            return this;
        }

        public ResponseBuilder setStreamName(String streamName) {
            this.streamName = streamName;
            return this;
        }

        public ResponseBuilder setGenres(List<String> genres) {
            this.genres = genres;
            return this;
        }

        public ResponseBuilder setWebsite(String website) {
            this.website = website;
            return this;
        }

        public StreamScrapper.Response build() {
            return new StreamScrapper.Response(
                    listingStatus,
                    format,
                    bitrate,
                    listenerPeak,
                    streamName,
                    genres,
                    website,
                    filledPropertyCount()
            );
        }

        public Optional<StreamScrapper.Response> buildAsOptional() {
            return Optional.of(build());
        }

        private int filledPropertyCount() {
            int propertyCount = 0;
            if (StringUtils.isNotBlank(listingStatus)) {
                propertyCount++;
            }

            if (Objects.nonNull(format)) {
                propertyCount++;
            }

            if (bitrate > 0) {
                propertyCount++;
            }

            if (listenerPeak > 0) {
                propertyCount++;
            }

            if (StringUtils.isNotBlank(streamName)) {
                propertyCount++;
            }

            if (genres.size() > 0) {
                propertyCount++;
            }

            if (StringUtils.isNotBlank(website)) {
                propertyCount++;
            }

            return propertyCount;
        }
    }
}
