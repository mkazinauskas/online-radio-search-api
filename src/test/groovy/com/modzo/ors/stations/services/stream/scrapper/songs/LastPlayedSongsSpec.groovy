package com.modzo.ors.stations.services.stream.scrapper.songs

import com.modzo.ors.stations.services.stream.reader.WebPageReader
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZonedDateTime

import static java.time.ZoneId.systemDefault

class LastPlayedSongsSpec extends Specification {

    void 'should scrap stream played songs from url'() {
        given:
            String url = 'http://192.168.169.34:92100/index.html?sid=2'
        and:
            LastPlayedSongsScrapper scrapper = prepareScrapper(url)
        and:
            LastPlayedSongsScrapper.Request request = new LastPlayedSongsScrapper.Request(url)
        when:
            LastPlayedSongsScrapper.Response scraped = scrapper.scrap(request).get()
        then:
            scraped.songs.size() == 20
            scraped.songs[0].playedTime.withZoneSameInstant(systemDefault()) == todayAtTime(15, 55, 02)
            scraped.songs[0].name == "Rihanna - If It's Lovin' That You Want"

            scraped.songs[1].playedTime.withZoneSameInstant(systemDefault()) == todayAtTime(15, 52, 03)
            scraped.songs[1].name == 'Luis Fonsi - Not on you (feat. Demi Lovato)'

            scraped.songs[2].playedTime.withZoneSameInstant(systemDefault()) == todayAtTime(15, 48, 56)
            scraped.songs[2].name == 'Journey - Any way you want it'

            scraped.songs[19].playedTime.withZoneSameInstant(systemDefault()) == todayAtTime(15, 15, 53)
            scraped.songs[19].name == 'Notiziario nazionale'
    }

    private static ZonedDateTime todayAtTime(int hour, int minute, int second) {
        return LocalDate.now().atTime(hour, minute, second).atZone(systemDefault())
    }

    private LastPlayedSongsScrapper prepareScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/played/played-source.html').text

        WebPageReader stub = Stub(WebPageReader) {
            read(url) >> Optional.of(new WebPageReader.Response(url, null, page))
        }

        return new LastPlayedSongsScrapper(stub)
    }
}
