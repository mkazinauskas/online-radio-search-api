package com.mozdzo.ors.services.scrapper.stream

import com.mozdzo.ors.services.scrapper.WebPageReader
import spock.lang.Specification

import static com.mozdzo.ors.services.scrapper.stream.StreamScrapper.Response.Format.MP3

class StreamScrapperSpec extends Specification {

    void 'should scrap the stream'() {
        given:
            String url = 'someUrl'
        and:
            StreamScrapper scrapper = prepareScrapper(url)
        when:
            StreamScrapper.Response scraped = scrapper.scrap(new StreamScrapper.Request(url)).get()
        then:
            scraped.listingStatus == 'Stream is currently up and public'
            scraped.format == MP3
            scraped.bitrate == 192
            scraped.listenerPeak == 411
            scraped.streamName == 'Radio 2.0 - Valli di Bergamo'
            scraped.genres == ['Pop', 'Rock', '80s', '70s', 'Top 40']
            scraped.website == 'www.radioduepuntozero.it'
    }

    StreamScrapper prepareScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/stream/sample-source.html').text

        return new StreamScrapper(Stub(WebPageReader) {
            read(url) >> Optional.of(page)
        })

    }
}
