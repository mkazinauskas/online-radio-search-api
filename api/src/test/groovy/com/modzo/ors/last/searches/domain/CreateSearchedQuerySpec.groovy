package com.modzo.ors.last.searches.domain

import com.modzo.ors.stations.domain.DomainException
import com.modzo.ors.stations.resources.IntegrationSpec
import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

import static org.apache.commons.lang3.StringUtils.EMPTY

class CreateSearchedQuerySpec extends IntegrationSpec {
    @Autowired
    CreateSearchedQuery.Handler handler

    @Autowired
    SearchedQueries searchedQueries

    void 'searched query should not be accepted if blank'() {
        when:
            handler.handle(new CreateSearchedQuery(EMPTY))
        then:
            DomainException exception = thrown(DomainException)
            exception.id == 'SEARCH_QUERY_NOT_BLANK'
            exception.message == 'Searched query was blank'
    }

    void 'searched query should be saved'() {
        given:
            String query = RandomStringUtils.randomAlphanumeric(40)
        when:
            SearchedQuery result = handler.handle(new CreateSearchedQuery(query))
        then:
            SearchedQuery savedQuery = searchedQueries.findById(result.id).get()
            savedQuery.created
            savedQuery.query == query
    }
}
