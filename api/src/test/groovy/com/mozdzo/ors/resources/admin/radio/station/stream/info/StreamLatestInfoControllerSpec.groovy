package com.mozdzo.ors.resources.admin.radio.station.stream.info

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.radio.station.commands.GetRadioStation
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.mozdzo.ors.TestUsers.ADMIN
import static com.mozdzo.ors.TokenProvider.token
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.NO_CONTENT

class StreamLatestInfoControllerSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStream.Handler radioStationStreamHandler

    @Autowired
    GetRadioStation.Handler radioStationHandler

    void 'admin should update latest radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            serverResponseExist(stream.url)
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
            updatedStream.type == RadioStationStream.Type.MP3
            updatedStream.bitRate == 192
        and:
            RadioStation updateRadioStation = radioStationHandler.handle(new GetRadioStation(radioStation.id))
            updateRadioStation.title == 'Radio 2.0 - Valli di Bergamo'
            updateRadioStation.website == 'www.radioduepuntozero.it'
            updateRadioStation.genres*.title.containsAll(['Pop', 'Rock', '80s', '70s', 'Top 40'])
    }

    private void serverResponseExist(String url) {
        String page = getClass().getResource('/services/scrappers/stream/sample-source.html').text

        testWiremockServer.server().stubFor(
                get(urlEqualTo('/' + url.split('/').last()))
                        .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(page))
        )
    }
}
