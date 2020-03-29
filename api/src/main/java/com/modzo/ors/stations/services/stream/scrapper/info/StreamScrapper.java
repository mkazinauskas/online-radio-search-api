package com.modzo.ors.stations.services.stream.scrapper.info;

import com.modzo.ors.stations.services.stream.WebPageReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class StreamScrapper {

    private final WebPageReader siteReader;

    private final List<StreamInfoScrappingStrategy> streamInfoScrappingStrategies;

    public StreamScrapper(WebPageReader siteReader,
                          List<StreamInfoScrappingStrategy> streamInfoScrappingStrategies) {
        this.siteReader = siteReader;
        this.streamInfoScrappingStrategies = streamInfoScrappingStrategies;
    }

    public Optional<Response> scrap(Request request) {
        return siteReader.read(request.url)
                .map(getResponseHavingHighestPropertyCount())
                .orElse(empty());
    }

    private Function<WebPageReader.Response, Optional<Response>> getResponseHavingHighestPropertyCount() {
        return response -> Optional.of(streamInfoScrappingStrategies.stream()
                .map(strategy -> strategy.extract(response))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(new ResponseBuilder(), this::map, this::addToResponseBuilder)
                .build());
    }

    private ResponseBuilder map(ResponseBuilder builder, Response newResponse) {
        Response currentResponse = builder.build();

        if (newResponse.getListenerPeak() > 0) {
            builder.setListenerPeak(newResponse.getListenerPeak());
        }

        boolean newFormatKnown = newResponse.getFormat() != Response.Format.UNKNOWN;
        boolean currentFormatUnknown = currentResponse.getFormat() == null
                || currentResponse.getFormat() == Response.Format.UNKNOWN;
        if (currentFormatUnknown && newFormatKnown) {
            builder.setFormat(newResponse.getFormat());
        }

        if (isNotBlank(newResponse.getListingStatus())) {
            builder.setListingStatus(newResponse.getListingStatus());
        }

        if (newResponse.getBitrate() > 0) {
            builder.setBitrate(newResponse.getBitrate());
        }

        if (isNotBlank(newResponse.getStreamName())) {
            builder.setStreamName(newResponse.getStreamName());
        }

        if (newResponse.getGenres().size() > currentResponse.getGenres().size()) {
            builder.setGenres(newResponse.getGenres());
        }

        if (isNotBlank(newResponse.getWebsite())) {
            builder.setWebsite(newResponse.getWebsite());
        }

        return builder;
    }

    private ResponseBuilder addToResponseBuilder(ResponseBuilder first, ResponseBuilder second) {
        return this.map(first, second.build());
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

        private final List<String> genres = new ArrayList<>();

        private final String website;

        private Response(String listingStatus,
                         Format format,
                         int bitrate,
                         int listenerPeak,
                         String streamName,
                         List<String> genres,
                         String website) {
            this.listingStatus = listingStatus;
            this.format = format;
            this.bitrate = bitrate;
            this.listenerPeak = listenerPeak;
            this.streamName = streamName;
            ofNullable(genres)
                    .ifPresent(this.genres::addAll);
            this.website = website;
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
                    website
            );
        }

        public Optional<StreamScrapper.Response> buildAsOptional() {
            return Optional.of(build());
        }
    }
}
