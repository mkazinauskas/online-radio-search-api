package com.modzo.ors.stations.services.stream.url

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStreamUrls
import com.modzo.ors.stations.resources.IntegrationSpec
import org.eclipse.jetty.http.HttpHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Unroll

class UrlResolverSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStreamUrls.Handler getStreamUrlsHandler

    @Autowired
    UrlResolver testTarget

    @Unroll
    void 'should resolve #urlType url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            String correctUrl = stream.url + urlEnding
        and:
            noServerResponseExist(stream.url)
            serverResponseExist(correctUrl)
        when:
            testTarget.resolve(stream.radioStationId, stream.id, urlType)
        then:
            Page<StreamUrl> urls = getStreamUrlsHandler.handle(
                    new GetRadioStationStreamUrls(stream.id, Pageable.unpaged())
            )
            urls.content.size() == 1
            StreamUrl expectedUrl = urls.content.find { it.type == urlType }
            expectedUrl.url == correctUrl
        where:
            urlEnding            | urlType
            '/played.html?sid=1' | StreamUrl.Type.SONGS
            '/index.html?sid=1'  | StreamUrl.Type.INFO
    }

    @Unroll
    void 'should not resolve #urlType url when not working'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            String correctUrl = stream.url + urlEnding
        and:
            noServerResponseExist(stream.url)
            noServerResponseExist(correctUrl)
        when:
            testTarget.resolve(stream.radioStationId, stream.id, urlType)
        then:
            Page<StreamUrl> urls = getStreamUrlsHandler.handle(
                    new GetRadioStationStreamUrls(stream.id, Pageable.unpaged())
            )
            urls.content.size() == 0
        where:
            urlEnding            | urlType
            '/played.html?sid=1' | StreamUrl.Type.SONGS
            '/index.html?sid=1'  | StreamUrl.Type.INFO
    }

    private void serverResponseExist(String url) {
        String body = getClass().getResource('/services/scrappers/played/played-source.html').text
        Map<String, String> headers = [(HttpHeader.CONTENT_TYPE.asString()): 'text/html']
        wireMockTestHelper.okGetResponse(url, headers, body)
    }

    private void noServerResponseExist(String url) {
        wireMockTestHelper.notFoundResponse(url)
    }
}