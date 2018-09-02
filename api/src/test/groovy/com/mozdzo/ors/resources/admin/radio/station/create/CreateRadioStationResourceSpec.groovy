package com.mozdzo.ors.resources.admin.radio.station.create

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static com.mozdzo.ors.TestUsers.ADMIN
import static com.mozdzo.ors.TokenProvider.token
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class CreateRadioStationResourceSpec extends IntegrationSpec {

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