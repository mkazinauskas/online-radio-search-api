package com.mozdzo.ors.resources.admin.radio.station.create

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.TestUsers
import com.mozdzo.ors.helpers.Location
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.CREATED
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class CreateRadioStationResourceSpec extends IntegrationSpec {

    def 'admin should create radio station'() {
        given:
            CreateRadioStationRequest request = new CreateRadioStationRequest(
                    title: randomAlphanumeric(100)
            )
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/radio-stations',
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(TestUsers.ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            response.statusCode == CREATED
            Location.formHeader(response.headers)> 0
    }
}