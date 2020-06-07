package com.modzo.ors.stations.resources.admin.genre.delete

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.DELETE
import static org.springframework.http.HttpStatus.OK

class DeleteGenreControllerSpec extends IntegrationSpec {

    void 'admin should delete genre'() {
        given:
            Genre genre = testGenre.create()
        when:
            ResponseEntity<String> result = restTemplate.exchange(
                    "/admin/genres/${genre.id}",
                    DELETE,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            result.statusCode == OK
        and:
            songs.findById(genre.id).isEmpty()
    }

}
