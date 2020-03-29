package com.modzo.ors.stations.services.stream.scrapper.info;

import com.modzo.ors.stations.services.stream.WebPageReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        return new StreamScrapper.ResponseBuilder()
                .setListingStatus(requiredTrs.getOrDefault("Listing Status:", ""))
                .setFormat(StreamScrapper.Response.Format.findFormat(requiredTrs.getOrDefault("Stream Status:", "")))
                .setBitrate(bitRate(requiredTrs.getOrDefault("Stream Status:", "")))
                .setListenerPeak(listenerPeak(requiredTrs.getOrDefault("Listener Peak:", "")))
                .setStreamName(requiredTrs.getOrDefault("Stream Name:", ""))
                .setGenres(genres(requiredTrs.getOrDefault("Stream Genre(s):", "")))
                .setWebsite(requiredTrs.getOrDefault("Stream Website:", ""))
                .buildAsOptional();
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
                .filter(StringUtils::isNotBlank)
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
