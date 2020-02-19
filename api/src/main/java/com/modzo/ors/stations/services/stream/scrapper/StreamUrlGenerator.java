package com.modzo.ors.stations.services.stream.scrapper;

import com.rainerhahnekamp.sneakythrow.Sneaky;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamUrlGenerator {

    private final List<String> endings;

    public StreamUrlGenerator(List<String> endings) {
        this.endings = endings;
    }

    public List<String> generateUrls(String url) {
        List<String> generatedUrls = new ArrayList<>();
        generatedUrls.add(url);
        generatedUrls.addAll(urlsWithAppendedEndings(url));
        generatedUrls.addAll(urlsWithReplacedPathsToEndings(url));
        return generatedUrls;
    }

    private List<String> urlsWithAppendedEndings(String url) {
        return endings.stream()
                .map(ending -> url + ending)
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
