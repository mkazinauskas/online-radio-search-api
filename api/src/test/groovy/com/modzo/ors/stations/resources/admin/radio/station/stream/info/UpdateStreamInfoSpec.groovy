package com.modzo.ors.stations.resources.admin.radio.station.stream.info

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.eclipse.jetty.http.HttpHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.NO_CONTENT

class UpdateStreamInfoSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStream.Handler radioStationStreamHandler

    @Autowired
    GetRadioStation.Handler radioStationHandler

    void 'admin should update info radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            StreamUrl streamUrl = testStreamUrl.create(radioStation.id, stream.id, StreamUrl.Type.INFO)
        and:
            serverResponseExist(streamUrl.url)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}/streams/${stream.id}/latest-info",
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
            RadioStationStream updatedStream = radioStationStreamHandler
                    .handle(new GetRadioStationStream(radioStation.id, stream.id))

            updatedStream.url == stream.url
            updatedStream.type.get() == RadioStationStream.Type.MP3
            updatedStream.bitRate == 192
            updatedStream.infoChecked
            updatedStream.working
        and:
            RadioStation updateRadioStation = radioStationHandler.handle(new GetRadioStation(radioStation.id))
            updateRadioStation.title == 'Radio 2.0 - Valli di Bergamo'
            updateRadioStation.website == 'www.radioduepuntozero.it'
            updateRadioStation.genres*.title.containsAll(['Pop', 'Rock', '80s', '70s', 'Top 40'])
    }

    void 'admin should set stream as not working, if failed to load it'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            StreamUrl streamUrl = testStreamUrl.create(radioStation.id, stream.id, StreamUrl.Type.INFO)
        and:
            noServerResponseExist(streamUrl.url)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}/streams/${stream.id}/latest-info",
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
            RadioStationStream updatedStream = radioStationStreamHandler
                    .handle(new GetRadioStationStream(radioStation.id, stream.id))

            updatedStream.infoChecked
            !updatedStream.working
    }

    private void serverResponseExist(String url) {
        String content = getClass().getResource('/services/scrappers/stream/sample-source.html').text
        Map<String, String> headers = [(HttpHeader.CONTENT_TYPE.asString()): 'text/html']
        wireMockTestHelper.okGetResponse(url, headers, content)
    }

    private void noServerResponseExist(String url) {
        wireMockTestHelper.notFoundResponse(url)
    }
}
