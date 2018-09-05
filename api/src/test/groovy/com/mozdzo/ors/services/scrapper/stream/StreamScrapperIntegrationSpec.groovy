package com.mozdzo.ors.services.scrapper.stream

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.mozdzo.ors.services.scrapper.stream.StreamScrapper.Response.Format.MP3

class StreamScrapperIntegrationSpec extends IntegrationSpec {

    @Autowired
    StreamScrapper testTarget

    void 'should scrap the stream'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            mockStream(stream.url)
        when:
            StreamScrapper.Response scraped = testTarget.scrap(new StreamScrapper.Request(stream.url)).get()
        then:
            scraped.listingStatus == 'Stream is currently up and public'
            scraped.format == MP3
            scraped.bitrate == 192
            scraped.listenerPeak == 411
            scraped.streamName == 'Radio 2.0 - Valli di Bergamo'
            scraped.genres == ['Pop', 'Rock', '80s', '70s', 'Top 40']
            scraped.website == 'www.radioduepuntozero.it'
    }

    private void mockStream(String url) {
        String page = getClass().getResource('/services/scrappers/stream/sample-source.html').text

        testWiremockServer.server().stubFor(
                get(urlEqualTo('/' + url.split('/').last()))
                        .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(page))
        )
    }
}
