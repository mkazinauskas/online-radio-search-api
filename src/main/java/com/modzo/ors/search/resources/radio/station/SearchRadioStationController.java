package com.modzo.ors.search.resources.radio.station;

import com.modzo.ors.search.domain.commands.SearchRadioStationByTitle;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class SearchRadioStationController {

    private final SearchRadioStationByTitle.Handler searchHandler;

    public SearchRadioStationController(SearchRadioStationByTitle.Handler searchHandler) {
        this.searchHandler = searchHandler;
    }

    @GetMapping(value = "/search/radio-station", params = {"title"})
    ResponseEntity<SearchRadioStationResultsModel> search(@RequestParam("title") String title, Pageable pageable) {
        Page<RadioStation> foundStations = searchHandler.handle(
                new SearchRadioStationByTitle(title, pageable)
        );
        return ok(SearchRadioStationResultsModel.create(foundStations, pageable, title));
    }
}