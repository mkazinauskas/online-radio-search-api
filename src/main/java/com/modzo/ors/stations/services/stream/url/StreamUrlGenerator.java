package com.modzo.ors.stations.services.stream.url;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.rainerhahnekamp.sneakythrow.Sneaky;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract class StreamUrlGenerator {

    private final List<String> endings;

    StreamUrlGenerator(List<String> endings) {
        this.endings = endings;
    }

    abstract StreamUrl.Type forType();

    List<String> generateUrls(String url) {
        List<String> generatedUrls = new ArrayList<>();
        generatedUrls.addAll(urlsWithAppendedEndings(url));
        generatedUrls.addAll(urlsWithReplacedPathsToEndings(url));
        return generatedUrls;
    }

    private List<String> urlsWithAppendedEndings(String url) {
        String urlWithoutSlash = url.replaceFirst("/*$", "");
        return endings.stream()
                .map(ending -> urlWithoutSlash + ending)
                .collect(Collectors.toList());
    }

    private List<String> urlsWithReplacedPathsToEndings(String url) {
        URL parsedUrl = Sneaky.sneak(() -> new URL(url));
        URI uri = Sneaky.sneak(parsedUrl::toURI);
        return endings.stream()
                .map(ending -> uri.resolve(ending).toString())
                .collect(Collectors.toList());
    }
}
