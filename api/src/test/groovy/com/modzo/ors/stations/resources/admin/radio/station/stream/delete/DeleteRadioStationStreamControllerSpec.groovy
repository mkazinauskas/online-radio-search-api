package com.modzo.ors.stations.resources.admin.radio.station.stream.delete

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpStatus.OK

class DeleteRadioStationStreamControllerSpec extends IntegrationSpec {

    void 'admin should delete radio station stream'() {
        given:
            RadioStationStream stream = testRadioStationStream.create()
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${stream.radioStationId}/streams/${stream.id}",
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