package com.mozdzo.ors.services.scrapper.stream

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

import static com.github.tomakehurst.wiremock.client.WireMock.*

class StreamScrapperSpec extends IntegrationSpec {

    @Autowired
    StreamScrapper testTarget

    void 'should scrap the stream'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            mockStream(stream.url)
        expect:
            testTarget.scrap(stream)
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
