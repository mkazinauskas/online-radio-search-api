package com.modzo.ors.stations.resources.admin.radio.station.stream.create

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.resources.IntegrationSpec
import com.modzo.ors.stations.resources.admin.radio.station.stream.create.CreateRadioStationStreamRequest
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.OK
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class CreateRadioStationStreamControllerSpec extends IntegrationSpec {

    void 'admin should create radio station'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            CreateRadioStationStreamRequest request = new CreateRadioStationStreamRequest(
                    url: "http://www.${randomAlphanumeric(14)}.com"
            )
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}/streams",
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(token(TestUsers.ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            restTemplate.getForEntity(response.headers.getLocation().path, String).statusCode == OK
    }
}
