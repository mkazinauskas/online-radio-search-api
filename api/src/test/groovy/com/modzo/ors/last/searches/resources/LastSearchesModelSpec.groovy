package com.modzo.ors.last.searches.resources

import com.modzo.ors.HttpEntityBuilder
import com.modzo.ors.last.searches.TestSearchQuery
import com.modzo.ors.last.searches.domain.SearchedQuery
import com.modzo.ors.stations.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static java.time.ZoneId.systemDefault
import static org.springframework.hateoas.IanaLinkRelations.SELF
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

class LastSearchesModelSpec extends IntegrationSpec {

    @Autowired
    TestSearchQuery testSearchQuery

    void 'should not fail to retrieve last searches, when none exist'() {
        given:
            String url = '/last-searches'
        when:
            ResponseEntity<LastSearchesModel> result = restTemplate.exchange(
                    "${url}?size=100&page=0",
                    GET,
                    HttpEntityBuilder.builder().build(),
                    LastSearchesModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body) {
                it.content != null

                links.first().rel == SELF
                links.first().href.endsWith(url)
            }
    }

    void 'anyone should retrieve last search'() {
        given:
            SearchedQuery searchedQuery = testSearchQuery.create()
        and:
            String url = '/last-searches'
        when:
            ResponseEntity<LastSearchesModel> result = restTemplate.exchange(
                    "${url}?size=100&page=0",
                    GET,
                    HttpEntityBuilder.builder().build(),
                    LastSearchesModel
            )
        then:
            result.statusCode == OK
        and:
            with(result.body as LastSearchesModel) {
                LastSearchResponse response = it.content
                        .find { it.content.id == searchedQuery.id }
                        .content

                response
                response.id == searchedQuery.id
                response.query == searchedQuery.query
                response.created.withZoneSameInstant(systemDefault()) == searchedQuery.created

                links.first().rel == SELF
                links.first().href.endsWith(url)
            }
    }
}