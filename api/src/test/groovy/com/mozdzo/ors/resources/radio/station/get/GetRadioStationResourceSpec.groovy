package com.mozdzo.ors.resources.radio.station.get

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.TestUsers
import com.mozdzo.ors.domain.station.RadioStation
import com.mozdzo.ors.helpers.TestRadioStation
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class GetRadioStationResourceSpec extends IntegrationSpec {

    @Autowired
    TestRadioStation testRadioStation

    def 'admin should retrieve radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        when:
            ResponseEntity<GetRadioStationResponse> response = restTemplate.exchange(
                    "/radio-stations/${radioStation.id}",
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(TestUsers.ADMIN))
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