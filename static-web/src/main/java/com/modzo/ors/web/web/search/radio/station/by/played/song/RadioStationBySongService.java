package com.modzo.ors.web.web.search.radio.station.by.played.song;

import com.modzo.ors.web.web.api.radio.stations.RadioStationResponse;
import com.modzo.ors.web.web.api.radio.stations.RadioStationsClient;
import com.modzo.ors.web.web.components.common.EnhancedPageMetadata;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class RadioStationBySongService {

    private final RadioStationsClient radioStationsClient;

    public RadioStationBySongService(RadioStationsClient radioStationsClient) {
        this.radioStationsClient = radioStationsClient;
    }

    Data retrieveStationBy(long songId, Pageable pageable) {
        var radioStationByPlayedSongId = radioStationsClient.getRadioStationByPlayedSongId(
                songId,
                pageable.getPageNumber()
        );
        PagedModel.PageMetadata metadata = radioStationByPlayedSongId.getMetadata();
        var radioStationStream = radioStationByPlayedSongId.getContent().stream()
                .map(EntityModel::getContent)
                .filter(Objects::nonNull)
                .map(Data.RadioStation::from)
                .collect(Collectors.toList());

        return new Data(radioStationStream, metadata);
    }

    static class Data {

        private final List<RadioStation> stations;

        private final EnhancedPageMetadata metadata;

        public Data(List<RadioStation> stations,
                    PagedModel.PageMetadata metadata) {
            this.stations = stations;
            this.metadata = new EnhancedPageMetadata(metadata);
        }

        static class RadioStation {

            private final long id;

            private final String title;

            private final String website;

            private RadioStation(long id, String title, String website) {
                this.id = id;
                this.title = title;
                this.website = website;
            }

            static RadioStation from(RadioStationResponse response) {
                return new RadioStation(
                        response.getId(),
                        response.getTitle(),
                        response.getWebsite()
                );
            }

            public long getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getWebsite() {
                return website;
            }
        }

        public List<RadioStation> getStations() {
            return stations;
        }

        public EnhancedPageMetadata getMetadata() {
            return metadata;
        }
    }
}
