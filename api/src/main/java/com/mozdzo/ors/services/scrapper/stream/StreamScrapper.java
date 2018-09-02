package com.mozdzo.ors.services.scrapper.stream;

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StreamScrapper {

    private final WebPageReader siteReader;

    public StreamScrapper(WebPageReader siteReader) {
        this.siteReader = siteReader;
    }

    public void scrap(RadioStationStream radioStationStream) {
        Optional<String> site = siteReader.read(radioStationStream.getUrl());
    }
}
