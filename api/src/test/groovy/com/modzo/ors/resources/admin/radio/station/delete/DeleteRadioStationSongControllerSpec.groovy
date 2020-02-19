package com.modzo.ors.resources.admin.radio.station.delete

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.domain.radio.station.RadioStation
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpStatus.OK

class DeleteRadioStationSongControllerSpec extends IntegrationSpec {

    void 'admin should delete radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}",
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