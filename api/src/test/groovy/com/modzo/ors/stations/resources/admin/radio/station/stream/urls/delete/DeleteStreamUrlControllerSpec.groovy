package com.modzo.ors.stations.resources.admin.radio.station.stream.urls.delete

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpStatus.OK

class DeleteStreamUrlControllerSpec extends IntegrationSpec {

    void 'admin should delete radio station stream url'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        and:
            StreamUrl streamUrl = testStreamUrl.create(stream.radioStationId, stream.id, StreamUrl.Type.INFO)
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}/urls/${streamUrl}",
                    DELETE,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
    }
}