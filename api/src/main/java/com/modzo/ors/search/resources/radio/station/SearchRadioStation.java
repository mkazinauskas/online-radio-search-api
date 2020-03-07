package com.modzo.ors.search.resources.radio.station;

import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.commands.SearchRadioStationByTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class SearchRadioStation {

    private final SearchRadioStationByTitle.Handler searchHandler;

    private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

    public SearchRadioStation(SearchRadioStationByTitle.Handler searchHandler,
                              CreateSearchedQuery.Handler lastSearchedQueryHandler) {
        this.searchHandler = searchHandler;
        this.lastSearchedQueryHandler = lastSearchedQueryHandler;
    }

    @GetMapping(value = "/search/radio-station", params = {"title"})
    ResponseEntity<SearchRadioStationResultsModel> search(@RequestParam("title") String title, Pageable pageable) {
        Page<RadioStationDocument> foundStations = searchHandler.handle(
                new SearchRadioStationByTitle(title, pageable)
        );
        lastSearchedQueryHandler.handle(new CreateSearchedQuery(title));
        return ok(SearchRadioStationResultsModel.create(foundStations, pageable, title));
    }
}