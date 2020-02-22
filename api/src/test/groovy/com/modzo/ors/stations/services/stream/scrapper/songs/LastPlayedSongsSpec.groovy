package com.modzo.ors.stations.services.stream.scrapper.songs

import com.modzo.ors.stations.services.stream.scrapper.WebPageReader
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.ZonedDateTime

import static java.time.ZoneId.systemDefault

class LastPlayedSongsSpec extends Specification {

    @Shared
    StreamPlayedSongsUrlGenerator generator = new StreamPlayedSongsUrlGenerator(
            ['/played.html', '/played.html?sid=1', '/played.html?sid=2']
    )

    @Unroll
    void 'should use `#streamLastPlayedUrl` to scrap stream played songs from `#streamUrl`'() {
        given:
            LastPlayedSongsScrapper scrapper = prepareScrapper(streamLastPlayedUrl)
        and:
            LastPlayedSongsScrapper.Request request = new LastPlayedSongsScrapper.Request(streamUrl)
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
        where:
            streamUrl                       | streamLastPlayedUrl
            'http://192.168.169.34:92100'   | 'http://192.168.169.34:92100'
            'http://192.168.169.34:92100'   | 'http://192.168.169.34:92100/played.html'
            'http://192.168.169.34:92100/;' | 'http://192.168.169.34:92100/;'
            'http://192.168.169.34:92100/;' | 'http://192.168.169.34:92100/played.html'
            'http://test.com/test/'         | 'http://test.com/test/played.html?sid=1'
            'http://test.com/test'          | 'http://test.com/test/played.html?sid=1'
            'http://test.com/test'          | 'http://test.com/played.html?sid=2'
    }

    private static ZonedDateTime todayAtTime(int hour, int minute, int second) {
        return LocalDate.now().atTime(hour, minute, second).atZone(systemDefault())
    }

    private LastPlayedSongsScrapper prepareScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/played/played-source.html').text

        WebPageReader stub = Stub(WebPageReader) {
            read(url) >> Optional.of(new WebPageReader.Response(null, page))
        }

        return new LastPlayedSongsScrapper(stub, generator)
    }
}
