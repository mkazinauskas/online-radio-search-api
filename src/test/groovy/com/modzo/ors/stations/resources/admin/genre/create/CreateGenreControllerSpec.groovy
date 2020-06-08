package com.modzo.ors.stations.resources.admin.genre.create

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.resources.IntegrationSpec
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.modzo.ors.TestUsers.ADMIN
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.OK

class CreateGenreControllerSpec extends IntegrationSpec {

    void 'unauthorized user should not create genre'() {
        when:
            ResponseEntity<String> result = restTemplate.exchange(
                    '/admin/genres',
                    POST,
                    HttpEntityBuilder.builder()
                            .body(new CreateGenreRequest(RandomStringUtils.randomAlphabetic(10)))
                            .build(),
                    String
            )
        then:
            result.statusCode == HttpStatus.UNAUTHORIZED
    }

    void 'admin should create genre'() {
        given:
            CreateGenreRequest request = new CreateGenreRequest(
                    RandomStringUtils.randomAlphabetic(10)
            )
        when:
            ResponseEntity<String> result = restTemplate.exchange(
                    '/admin/genres',
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .body(request)
                            .build(),
                    String
            )
        then:
            result.statusCode == HttpStatus.CREATED
        and:
            restTemplate.getForEntity(result.headers.getLocation().path, String).statusCode == OK
    }

}
