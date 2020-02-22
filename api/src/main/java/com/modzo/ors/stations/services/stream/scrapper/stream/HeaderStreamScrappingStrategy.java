package com.modzo.ors.stations.services.stream.scrapper.stream;

import com.modzo.ors.stations.services.stream.scrapper.WebPageReader;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class HeaderStreamScrappingStrategy implements StreamInfoScrappingStrategy {
    @Override
    public Optional<StreamScrapper.Response> extract(WebPageReader.Response data) {
        return Optional.empty();
    }
}
