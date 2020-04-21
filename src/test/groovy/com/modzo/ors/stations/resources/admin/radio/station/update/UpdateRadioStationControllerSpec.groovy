package com.modzo.ors.stations.resources.admin.radio.station.update

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.TestUsers
import com.modzo.ors.stations.domain.radio.station.RadioStation
import com.modzo.ors.stations.domain.radio.station.RadioStations
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.PATCH
import static org.springframework.http.HttpStatus.ACCEPTED
import static org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

class UpdateRadioStationControllerSpec extends IntegrationSpec {

    @Autowired
    RadioStations radioStations

    void 'admin should update radio station'() {
        given:
            Genre genre = testGenre.create()

            RadioStation radioStation = testRadioStation.create()
        and:
            UpdateRadioStationRequest request = new UpdateRadioStationRequest(
                    title: randomAlphanumeric(100),
                    website: "http://${randomAlphanumeric(50)}.com",
                    enabled: true,
                    genres: [new UpdateRadioStationRequest.Genre(id: genre.id)]
            )
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}",
                    PATCH,
                    HttpEntityBuilder.builder()
                            .bearer(token(TestUsers.ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            response.statusCode == ACCEPTED
        then:
            with(radioStations.findById(radioStation.id).get()) {
                title == request.title
                website == request.website
                enabled == request.enabled
                genres.size() == 1
                genres.first().id == genre.id
            }
    }

}
