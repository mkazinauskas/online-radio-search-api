package com.modzo.ors.stations.services.stream.scrapper.songs

import com.modzo.ors.stations.services.stream.scrapper.WebPageReader
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZonedDateTime

import static java.time.ZoneId.systemDefault

class LastPlayedSongsSpec extends Specification {

    void 'should scrap stream last played songs'() {
        given:
            String url = 'someUrl'
        and:
            LastPlayedSongsScrapper scrapper = prepareScrapper(url)
        when:
            LastPlayedSongsScrapper.Response scraped = scrapper.scrap(new LastPlayedSongsScrapper.Request(url)).get()
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

    ZonedDateTime todayAtTime(int hour, int minute, int second) {
        return LocalDate.now().atTime(hour, minute, second).atZone(systemDefault())
    }

    LastPlayedSongsScrapper prepareScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/played/played-source.html').text

        return new LastPlayedSongsScrapper(Stub(WebPageReader) {
            read(url) >> Optional.of(page)
        })
    }
}
