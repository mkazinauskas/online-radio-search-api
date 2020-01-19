package com.modzo.ors.searches.resources;

import com.modzo.ors.searches.domain.SearchedQuery;
import com.modzo.ors.searches.domain.commands.GetLastSearchQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class LastSearchesController {
    private final GetLastSearchQueries.Handler handler;

    public LastSearchesController(GetLastSearchQueries.Handler handler) {
        this.handler = handler;
    }

    @GetMapping("/last-searches")
    ResponseEntity<PagedModel<LastSearchResource>> getLastSearches(Pageable pageable) {
        Page<SearchedQuery> queries = handler.handle(new GetLastSearchQueries(pageable));
        return ok(LastSearchesResource.create(queries, pageable));
    }

}
