package com.modzo.ors.search.domain.commands;

import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

public class SearchRadioStationByTitle {

    private final String title;

    private final Pageable pageable;

    public SearchRadioStationByTitle(String songName, Pageable pageable) {
        this.title = songName;
        this.pageable = pageable;
    }

    @Component
    public static class Handler {

        private final RadioStations radioStations;

        private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

        public Handler(RadioStations radioStations,
                       CreateSearchedQuery.Handler lastSearchedQueryHandler) {
            this.radioStations = radioStations;
            this.lastSearchedQueryHandler = lastSearchedQueryHandler;
        }

        public Page<RadioStation> handle(SearchRadioStationByTitle command) {
            Page<RadioStation> result = radioStations.findAllByTitleAndEnabledTrue(command.title, command.pageable);

            if (result.getNumberOfElements() > 0) {
                lastSearchedQueryHandler.handle(
                        new CreateSearchedQuery(command.title, SearchedQuery.Type.RADIO_STATION)
                );
            }

            return result;
        }
    }
}