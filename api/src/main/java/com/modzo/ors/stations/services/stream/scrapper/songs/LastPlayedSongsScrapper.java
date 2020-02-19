package com.modzo.ors.stations.services.stream.scrapper.songs;

import com.modzo.ors.stations.services.stream.scrapper.WebPageReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public class LastPlayedSongsScrapper {

    private final WebPageReader siteReader;

    private final StreamPlayedSongsUrlGenerator generator;

    public LastPlayedSongsScrapper(WebPageReader siteReader,
                                   StreamPlayedSongsUrlGenerator generator) {
        this.siteReader = siteReader;
        this.generator = generator;
    }

    public Optional<LastPlayedSongsScrapper.Response> scrap(LastPlayedSongsScrapper.Request request) {
        return generator.generateUrls(request.url)
                .stream()
                .map(this.siteReader::read)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::extract)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private Optional<LastPlayedSongsScrapper.Response> extract(String pageBody) {
        Document document = Jsoup.parse(pageBody);
        List<Element> tables = new ArrayList<>(document.getElementsByTag("table"));
        List<Element> trs = tables.stream()
                .map(element -> element.getElementsByTag("tr"))
                .flatMap(Collection::stream)
                .collect(toList());
        Map<String, String> requiredTrs = tableValues(trs);
        List<Response.PlayedSong> playedSongs = toSongList(requiredTrs);
        if (CollectionUtils.isEmpty(playedSongs)) {
            return empty();
        } else {
            return Optional.of(new LastPlayedSongsScrapper.Response(playedSongs));
        }
    }

    private List<Response.PlayedSong> toSongList(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .map(entry -> new Response.PlayedSong(toLocalDateTime(entry.getKey()), entry.getValue().trim()))
                .sorted(Comparator.comparing(Response.PlayedSong::getPlayedTime).reversed())
                .collect(toList());
    }

    private ZonedDateTime toLocalDateTime(String time) {
        return LocalDate.now().atTime(LocalTime.parse(time)).atZone(ZoneId.systemDefault());
    }

    private Map<String, String> tableValues(List<Element> elements) {
        return elements.stream()
                .filter(element -> getTd(element).size() >= 2)
                .filter(element -> getTd(element).get(0).text().length() == 8)
                .filter(element -> containsTwoCharacters(getTd(element).get(0).text(), ":"))
                .collect(toMap(element -> getTd(element).get(0).text(), element -> getTd(element).get(1).text()));
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
            private final ZonedDateTime playedTime;
            private final String name;

            public PlayedSong(ZonedDateTime playedTime, String name) {
                this.playedTime = playedTime;
                this.name = name;
            }

            public ZonedDateTime getPlayedTime() {
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
