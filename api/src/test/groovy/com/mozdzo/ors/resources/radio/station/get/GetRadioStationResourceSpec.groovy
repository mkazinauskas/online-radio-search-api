package com.mozdzo.ors.resources.radio.station.get

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class GetRadioStationResourceSpec extends IntegrationSpec {

    def 'anyone should retrieve radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        when:
            ResponseEntity<GetRadioStationResponse> response = restTemplate.exchange(
                    "/radio-stations/${radioStation.id}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    GetRadioStationResponse
            )
        then:
            response.statusCode == OK
        and:
            with(response.body) {
                id == radioStation.id
                title == radioStation.title
            }
    }
}