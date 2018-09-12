package com.mozdzo.ors.services.scrapper.songs;

import com.mozdzo.ors.services.scrapper.WebPageReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public class LastPlayedSongsScrapper {

    private final WebPageReader siteReader;

    public LastPlayedSongsScrapper(WebPageReader siteReader) {
        this.siteReader = siteReader;
    }

    public Optional<LastPlayedSongsScrapper.Response> scrap(LastPlayedSongsScrapper.Request request) {
        Optional<String> site = siteReader.read(request.getUrl());
        if (!site.isPresent()) {
            return empty();
        }
        Document document = Jsoup.parse(site.get());
        List<Element> tables = new ArrayList<>(document.getElementsByTag("table"));
        List<Element> trs = tables.stream()
                .map(element -> element.getElementsByTag("tr"))
                .flatMap(Collection::stream)
                .collect(toList());
        Map<String, String> requiredTrs = tableValues(trs);
        return Optional.of(new LastPlayedSongsScrapper.Response(
                toSongList(requiredTrs)
        ));
    }

    private List<Response.PlayedSong> toSongList(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .map(entry -> new Response.PlayedSong(toLocalDateTime(entry.getKey()), entry.getValue().trim()))
                .sorted(Comparator.comparing(Response.PlayedSong::getPlayedTime).reversed())
                .collect(toList());
    }

    private LocalDateTime toLocalDateTime(String time) {
        return LocalDate.now().atTime(LocalTime.parse(time));
    }

    private Map<String, String> tableValues(List<Element> elements) {
        return elements.stream()
                .filter(element -> getTd(element).size() >= 2)
                .filter(element -> getTd(element).get(0).text().length() == 8)
                .filter(element -> containsTwoCharacters(getTd(element).get(0).text(), ":"))
                .collect(toMap(
                        element -> getTd(element).get(0).text(),
                        element -> getTd(element).get(1).text()
                ));
    }

    private boolean containsTwoCharacters(String text, String character) {
        return text.replace(character, "").length() + 2 == text.length();
    }

    private Elements getTd(Element element) {
        return element.getElementsByTag("td");
    }


    public static class Request {
        private final String url;

        public Request(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class Response {
        private final List<PlayedSong> songs;

        Response(List<PlayedSong> songs) {
            this.songs = songs;
        }

        public static class PlayedSong {
            private final LocalDateTime playedTime;
            private final String name;

            public PlayedSong(LocalDateTime playedTime, String name) {
                this.playedTime = playedTime;
                this.name = name;
            }

            public LocalDateTime getPlayedTime() {
                return playedTime;
            }

            public String getName() {
                return name;
            }
        }

        public List<PlayedSong> getSongs() {
            return songs;
        }
    }
}
