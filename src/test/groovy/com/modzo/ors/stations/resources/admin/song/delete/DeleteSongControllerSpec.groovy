package com.modzo.ors.stations.resources.admin.song.delete

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.song.Song
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.OK

class DeleteSongControllerSpec extends IntegrationSpec {

    void 'admin should delete song'() {
        given:
            Song song = testSong.create()
        when:
            ResponseEntity<String> result = restTemplate.exchange(
                    "/admin/songs/${song.id}",
                    DELETE,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            result.statusCode == OK
        and:
            songs.findById(song.id).isEmpty()
    }

}
