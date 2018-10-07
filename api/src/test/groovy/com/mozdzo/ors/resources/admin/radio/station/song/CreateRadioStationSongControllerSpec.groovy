package com.mozdzo.ors.resources.admin.radio.station.song

import com.mozdzo.ors.HttpEntityBuilder
import com.mozdzo.ors.domain.radio.station.RadioStation
import com.mozdzo.ors.domain.song.Song
import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static com.mozdzo.ors.TestUsers.ADMIN
import static com.mozdzo.ors.TokenProvider.token
import static java.time.ZonedDateTime.now
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

class CreateRadioStationSongControllerSpec extends IntegrationSpec {

    void 'admin should create radio station song'() {
        given:
            RadioStation radioStation = testRadioStation.create()
        and:
            Song song = testSong.create()
        and:
            CreateRadioStationSongRequest request = new CreateRadioStationSongRequest(
                    songId: song.id,
                    playedTime: now()
            )
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/radio-stations/${radioStation.id}/songs",
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
