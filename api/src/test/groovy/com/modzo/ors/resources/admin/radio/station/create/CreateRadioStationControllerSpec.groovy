package com.modzo.ors.resources.admin.radio.station.create

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static com.modzo.ors.TokenProvider.token
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class CreateRadioStationControllerSpec extends IntegrationSpec {

    void 'admin should create radio station'() {
        given:
            CreateRadioStationRequest request = new CreateRadioStationRequest(
                    title: randomAlphanumeric(100)
            )
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/radio-stations',
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            response.statusCode == CREATED
        and:
            restTemplate.getForEntity(response.headers.getLocation().path, String).statusCode == OK
    }
}