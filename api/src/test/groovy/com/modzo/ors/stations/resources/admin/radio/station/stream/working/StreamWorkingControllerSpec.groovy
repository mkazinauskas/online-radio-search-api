package com.modzo.ors.stations.resources.admin.radio.station.stream.working

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream
import com.modzo.ors.stations.domain.song.commands.GetSong
import com.modzo.ors.stations.resources.IntegrationSpec
import org.eclipse.jetty.http.HttpHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import spock.lang.Unroll

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.NO_CONTENT

class StreamWorkingControllerSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStream.Handler radioStationStreamHandler

    @Autowired
    GetSong.Handler getSongHandler

    @Unroll
    void 'admin should check when stream working with type = `#type`'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            serverResponseExist(stream.url, type)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}/working",
                    POST,
                    HttpEntityBuilder
                            .builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == NO_CONTENT
        and:
            RadioStationStream updatedStream = radioStationStreamHandler.handle(
                    new GetRadioStationStream(stream.radioStationId, stream.id)
            )
            updatedStream.working
            updatedStream.checked
        where:
            type << [
                    'audio/mp3',
                    'audio/mpeg'
            ]
    }

    @Unroll
    void 'admin should check when stream not working with type = `#type`'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            serverResponseExist(stream.url, type)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}/working",
                    POST,
                    HttpEntityBuilder
                            .builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == NO_CONTENT
        and:
            RadioStationStream updatedStream = radioStationStreamHandler.handle(
                    new GetRadioStationStream(stream.radioStationId, stream.id)
            )
            !updatedStream.working
            updatedStream.checked
        where:
            type << [
                    'text/html',
                    'text/xml'
            ]
    }

    void 'admin should check when stream is not working'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            noServerResponseExist(stream.url)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}/working",
                    POST,
                    HttpEntityBuilder
                            .builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == NO_CONTENT
        and:
            RadioStationStream updatedStream = radioStationStreamHandler.handle(
                    new GetRadioStationStream(stream.radioStationId, stream.id)
            )
            !updatedStream.working
            updatedStream.checked
    }

    private void serverResponseExist(String url, String type) {
        Map<String, String> headers = [(HttpHeader.CONTENT_TYPE.asString()): type]
        wireMockTestHelper.okGetResponse(url, headers, '')
    }

    private void noServerResponseExist(String url) {
        wireMockTestHelper.notFoundResponse(url)
    }
}