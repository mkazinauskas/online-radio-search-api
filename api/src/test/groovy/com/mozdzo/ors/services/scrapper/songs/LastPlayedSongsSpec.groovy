package com.mozdzo.ors.services.scrapper.songs

import com.mozdzo.ors.services.scrapper.WebPageReader
import spock.lang.Specification

import static java.time.LocalDate.now

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
            scraped.songs[0].playedTime.toString() == now().atTime(15, 55, 02).toString()
            scraped.songs[0].name == "Rihanna - If It's Lovin' That You Want"
            scraped.songs[1].playedTime.toString() == now().atTime(15, 52, 03).toString()
            scraped.songs[1].name == 'Luis Fonsi - Not on you (feat. Demi Lovato)'
            scraped.songs[2].playedTime.toString() == now().atTime(15, 48, 56).toString()
            scraped.songs[2].name == 'Journey - Any way you want it'
            scraped.songs[19].playedTime.toString() == now().atTime(15, 15, 53).toString()
            scraped.songs[19].name == 'Notiziario nazionale'
    }

    LastPlayedSongsScrapper prepareScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/played/played-source.html').text

        return new LastPlayedSongsScrapper(Stub(WebPageReader) {
            read(url) >> Optional.of(page)
        })
    }
}
