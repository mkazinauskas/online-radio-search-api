package com.modzo.ors.search.resources.genre

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.stations.domain.radio.station.genre.Genre
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.http.ResponseEntity
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import spock.lang.Unroll

import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class SearchGenresControllerSpec extends IntegrationSpec {

    @Unroll
    void 'should find genre by title #genreTitle'() {
        given:
            Genre genre = testGenre.create(genreTitle)
        and:
            String url = '/search/genre'
        when:
            ResponseEntity<SearchGenreResultsModel> result = restTemplate.exchange(
                    url + "?title=${query}",
                    GET,
                    HttpEntityBuilder.builder()
                            .build(),
                    SearchGenreResultsModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.content.size() == 1
                with(it.content.first()) {
                    it.id == genre.id
                    it.uniqueId == genre.uniqueId
                    it.title == genre.title
                }
                with(it.links.first()) {
                    rel == SELF
                    String encodedQuery = query.replaceAll(' ', '%20')
                    href.endsWith("${url}?title=${encodedQuery}")
                }
            }
        where:
            genreTitle                                                  || query
            "${randomTitle()} tratret ${randomTitle()}"                 || 'tratret'
            "${randomTitle()} partl2g60l go ${randomTitle()}"           || 'tl2g60'
    }

    private static String randomTitle() {
        return RandomStringUtils.randomAlphanumeric(6)
    }

}