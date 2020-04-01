package com.modzo.ors.search.resources.genre

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SearchEventsControllerSpec extends IntegrationSpec {

    void 'should find genre by title'() {
        given:
            Genre genre = testGenre.create()
        and:
            eventsProcessor.process()
        and:
            String url = '/search/genre'
        when:
            ResponseEntity<SearchGenreResultsModel> result = restTemplate.exchange(
                    url + "?title=${genre.title}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SearchGenreResultsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                with(it.content.first()) {
                    it.id == genre.id
                    it.uniqueId == genre.uniqueId
                    it.title == genre.title
                }
                with(it.links.first()) {
                    rel == SELF
                    href.endsWith("${url}?title=${genre.title}")
                }
            }
    }

}