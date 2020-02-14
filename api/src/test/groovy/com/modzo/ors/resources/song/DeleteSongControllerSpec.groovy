package com.modzo.ors.resources.song

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.domain.song.Song
import com.modzo.ors.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpStatus.OK

class DeleteSongControllerSpec extends IntegrationSpec {

    void 'admin should delete song'() {
        given:
            Song song = testSong.create()
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    "/admin/songs/${song.id}",
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