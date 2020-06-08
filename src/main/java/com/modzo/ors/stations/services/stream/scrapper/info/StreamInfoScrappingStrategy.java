package com.modzo.ors.stations.services.stream.scrapper.info;

import com.modzo.ors.stations.services.stream.reader.WebPageReader;

import java.util.Optional;

interface StreamInfoScrappingStrategy {

    Optional<StreamScrapper.Response> extract(WebPageReader.Response data);
}
