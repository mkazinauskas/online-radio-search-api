package com.modzo.ors.stations.resources.admin.radio.station.stream.urls

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

import static com.modzo.ors.TestUsers.getADMIN
import static org.springframework.http.HttpMethod.PUT
import static org.springframework.http.HttpStatus.ACCEPTED

class ResolveUrlControllerSpec extends IntegrationSpec {

    @Autowired
    GetRadioStationStream.Handler radioStationStreamHandler

    @Autowired
    GetRadioStation.Handler radioStationHandler

    void 'admin should have possibility to resolve url'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            RadioStationStream stream = testRadioStationStream.create(radioStation.id)
        and:
            serverResponseExist(stream.url)
        and:
            ResolveUrlRequest request = new ResolveUrlRequest(StreamUrl.Type.INFO)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}/streams/${stream.id}/urls",
                    PUT,
                    HttpEntityBuilder
                            .builder()
                            .bearer(token(ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            response.statusCode == ACCEPTED
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

    private void serverResponseExist(String url) {
        String body = getClass().getResource('/services/scrappers/played/played-source.html').text
        Map<String, String> headers = [(HttpHeader.CONTENT_TYPE.asString()): 'text/html']
        wireMockTestHelper.okGetResponse(url, headers, body)
    }
}
