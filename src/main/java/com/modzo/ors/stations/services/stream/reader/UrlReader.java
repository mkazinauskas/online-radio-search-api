package com.modzo.ors.stations.services.stream.reader;

import java.util.Optional;

interface UrlReader {
    Optional<WebPageReader.Response> read(String url);
}
