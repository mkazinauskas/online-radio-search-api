package com.modzo.ors.last.searches

import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery
import org.springframework.stereotype.Component

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Component
class TestSearchQuery {

    private final CreateSearchedQuery.Handler handler

    TestSearchQuery(CreateSearchedQuery.Handler handler) {
        this.handler = handler
    }

    com.modzo.ors.last.searches.domain.SearchedQuery create() {
        return handler.handle(new CreateSearchedQuery(randomQuery()))
    }

    static String randomQuery() {
        randomAlphanumeric(100)
    }
}
