package com.modzo.ors.stations.resources.genre

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class GenreControllerSpec extends IntegrationSpec {

    void 'anyone should retrieve genres'() {
        given:
            Genre genre = testGenre.create()
        and:
            String url = '/genres'
        when:
            ResponseEntity<GenresModel> result = restTemplate.exchange(
                    url + '?size=100&page=0',
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    GenresModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as GenresModel) {
                GenreModel resource = it.content.find { it.content.id == genre.id }

                with(resource.content) {
                    title == genre.title
                }

                with(resource.links.first()) {
                    rel == SELF
                    href.endsWith("${url}/${genre.id}")
                }

                with(links.first()) {
                    rel == SELF
                    href.endsWith(url)
                }
            }
    }

    void 'anyone should retrieve genre'() {
        given:
            Genre genre = testGenre.create()
        and:
            String url = "/genres/${genre.id}"
        when:
            ResponseEntity<GenreModel> result = restTemplate.exchange(
                    url,
                    GET,
                    HttpEntityBuilder.builder().build(),
                    GenreModel
            )
        then:
            result.statusCode == OK
        and:
            GenreModel model = result.body
            with(model.content) {
                it.id == genre.id
                it.title == genre.title
            }
            with(model.links.first()) {
                rel == SELF
                href.endsWith(url)
            }
    }
}
