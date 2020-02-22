package com.modzo.ors.stations.services.stream.scrapper.stream;

import com.modzo.ors.stations.services.stream.scrapper.WebPageReader;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

@Component
class DefaultStreamScrappingStrategy implements StreamInfoScrappingStrategy {
    @Override
    public Optional<StreamScrapper.Response> extract(WebPageReader.Response data) {
        return data.getBody()
                .flatMap(this::extract);
    }

    private Optional<StreamScrapper.Response> extract(String body) {
        Document document = Jsoup.parse(body);
        List<Element> tables = new ArrayList<>(document.getElementsByTag("table"));
        List<Element> trs = tables.stream()
                .map(element -> element.getElementsByTag("tr"))
                .flatMap(Collection::stream)
                .collect(toList());
        Map<String, String> requiredTrs = tableValues(trs);
        return Optional.of(
                new StreamScrapper.Response(
                        requiredTrs.getOrDefault("Listing Status:", ""),
                        StreamScrapper.Response.Format.findFormat(requiredTrs.getOrDefault("Stream Status:", "")),
                        bitRate(requiredTrs.getOrDefault("Stream Status:", "")),
                        listenerPeak(requiredTrs.getOrDefault("Listener Peak:", "")),
                        requiredTrs.getOrDefault("Stream Name:", ""),
                        genres(requiredTrs.getOrDefault("Stream Genre(s):", "")),
                        requiredTrs.getOrDefault("Stream Website:", "")
                )
        );
    }

    private int listenerPeak(String line) {
        String cleanLine = line.trim();
        if (isCreatable(cleanLine)) {
            return parseInt(cleanLine);
        }
        return 0;
    }

    private List<String> genres(String genresLine) {
        return Stream.of(genresLine.split(" , "))
                .map(String::trim)
                .collect(toList());
    }

    private int bitRate(String line) {
        return Stream.of(line.split(" "))
                .filter(NumberUtils::isCreatable)
                .map(Integer::valueOf)
                .findFirst().orElse(0);
    }

    private Map<String, String> tableValues(List<Element> elements) {
        return elements.stream()
                .filter(element -> getTd(element).size() == 2)
                .collect(toMap(element -> getTd(element).get(0).text(), element -> getTd(element).get(1).text()));
    }

    private Elements getTd(Element element) {
        return element.getElementsByTag("td");
    }
}
