package com.mozdzo.ors.resources.admin.radio.station.song

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.TestUsers
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static java.time.LocalDateTime.now
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class CreateSongControllerSpec extends IntegrationSpec {

    def 'admin should create radio station song'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            CreateSongRequest request = new CreateSongRequest(
                    title: randomAlphanumeric(100),
                    playedTime: now()
            )
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}/songs",
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(TestUsers.ADMIN))
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
