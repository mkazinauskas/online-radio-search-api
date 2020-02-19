package com.modzo.ors.stations.services.stream.scrapper.stream

import com.modzo.ors.stations.services.stream.scrapper.WebPageReader
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.modzo.ors.stations.services.stream.scrapper.stream.StreamScrapper.Response.Format.MP3

class StreamScrapperSpec extends Specification {

    @Shared
    StreamInfoUrlGenerator generator = new StreamInfoUrlGenerator(['/info.html', '/info.html?si=1'])

    @Unroll
    void 'should use `#streamInfoUrl` to scrap stream info from `#streamUrl`'() {
        given:
            StreamScrapper scrapper = prepareScrapper(streamInfoUrl)
        when:
            StreamScrapper.Response scraped = scrapper.scrap(new StreamScrapper.Request(streamUrl)).get()
        then:
            scraped.listingStatus == 'Stream is currently up and public'
            scraped.format == MP3
            scraped.bitrate == 192
            scraped.listenerPeak == 411
            scraped.streamName == 'Radio 2.0 - Valli di Bergamo'
            scraped.genres == ['Pop', 'Rock', '80s', '70s', 'Top 40']
            scraped.website == 'www.radioduepuntozero.it'
        where:
            streamUrl                     | streamInfoUrl
            'http://192.168.169.34:92100' | 'http://192.168.169.34:92100'
            'http://192.168.169.34:92100' | 'http://192.168.169.34:92100/info.html'
            'http://test.com/test'        | 'http://test.com/test/info.html?si=1'
            'http://test.com/test'        | 'http://test.com/info.html?si=1'
    }

    StreamScrapper prepareScrapper(String url) {
        String page = getClass().getResource('/services/scrappers/stream/sample-source.html').text

        WebPageReader webPageReaderStub = Stub(WebPageReader) {
            read(url) >> Optional.of(page)
        }
        return new StreamScrapper(webPageReaderStub, generator)
    }
}
