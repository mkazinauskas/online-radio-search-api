package com.modzo.ors.web.web.search.radio.station;

import com.modzo.ors.web.web.api.search.radio.station.SearchRadioStationClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class SearchByRadioStationService {

    private final SearchRadioStationClient searchRadioStationClient;

    SearchByRadioStationService(SearchRadioStationClient searchRadioStationClient) {
        this.searchRadioStationClient = searchRadioStationClient;
    }

    Data retrieve(String title) {
        var foundSongs = searchRadioStationClient.searchRadioStationByTitle(title);
        var content = foundSongs.getContent();
        var convertedSongs = content.stream()
                .map(song -> new Data.RadioStation(song.getId(), song.getTitle()))
                .collect(Collectors.toList());

        return new Data(convertedSongs);

    }

    static class Data {

        private final List<RadioStation> radioStations;

        public Data(List<RadioStation> radioStations) {
            this.radioStations = radioStations;
        }

        static class RadioStation {

            private final long id;

            private final String title;

            public RadioStation(long id, String title) {
                this.id = id;
                this.title = title;
            }

            public long getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }
        }


    }
}
