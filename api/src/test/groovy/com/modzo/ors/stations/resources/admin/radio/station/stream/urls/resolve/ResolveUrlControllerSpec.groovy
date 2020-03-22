package com.modzo.ors.stations.resources.admin.radio.station.stream.urls.resolve

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.eclipse.jetty.http.HttpHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.PUT
import static org.springframework.http.HttpStatus.ACCEPTED

class ResolveUrlControllerSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStream.Handler radioStationStreamHandler

    @Autowired
    GetRadioStation.Handler radioStationHandler

    void 'admin should resolve info url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            String streamInfoUrl = stream.url + '/index.html'
        and:
            serverResponseExist(streamInfoUrl)
        and:
            ResolveUrlRequest request = new ResolveUrlRequest(StreamUrl.Type.INFO)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}/urls",
                    PUT,
                    HttpEntityBuilder
                            .builder()
                            .bearer(token(TestUsers.ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            response.statusCode == ACCEPTED
        and:
            RadioStationStream updatedStream = radioStationStreamHandler
                    .handle(new GetRadioStationStream(stream.radioStationId, stream.id))

            StreamUrl savedUrl = updatedStream.findUrl(StreamUrl.Type.INFO).get()
            savedUrl.type == StreamUrl.Type.INFO
            savedUrl.url == streamInfoUrl
    }

    void 'admin should resolve last songs url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            String streamInfoUrl = stream.url + '/played.html'
        and:
            serverResponseExist(streamInfoUrl)
        and:
            ResolveUrlRequest request = new ResolveUrlRequest(StreamUrl.Type.SONGS)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}/urls",
                    PUT,
                    HttpEntityBuilder
                            .builder()
                            .bearer(token(TestUsers.ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            response.statusCode == ACCEPTED
        and:
            RadioStationStream updatedStream = radioStationStreamHandler
                    .handle(new GetRadioStationStream(stream.radioStationId, stream.id))

            StreamUrl savedUrl = updatedStream.findUrl(StreamUrl.Type.SONGS).get()
            savedUrl.type == StreamUrl.Type.SONGS
            savedUrl.url == streamInfoUrl
    }

    private void serverResponseExist(String url) {
        String body = getClass().getResource('/services/scrappers/played/played-source.html').text
        Map<String, String> headers = [(HttpHeader.CONTENT_TYPE.asString()): 'text/html']
        wireMockTestHelper.okGetResponse(url, headers, body)
    }
}