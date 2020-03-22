package com.modzo.ors.stations.resources.admin.radio.station.stream.urls.create

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.OK
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class CreateStreamUrlControllerSpec extends IntegrationSpec {

    void 'admin should create radio station stream url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            CreateStreamUrlRequest request = new CreateStreamUrlRequest(
                    StreamUrl.Type.SONGS,
                    "http://www.${randomAlphanumeric(14)}.com"
            )
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}/urls",
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(token(TestUsers.ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            restTemplate.getForEntity(response.headers.getLocation().path, String).statusCode == OK
        and:
            RadioStationStream updatedStream = radioStationStreams.findById(stream.id).get()
            updatedStream.findUrl(StreamUrl.Type.SONGS).get().url == request.urls
    }
}