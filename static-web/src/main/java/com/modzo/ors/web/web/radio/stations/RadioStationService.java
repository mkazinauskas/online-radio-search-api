package com.modzo.ors.web.web.radio.stations;

import com.modzo.ors.web.web.api.radio.stations.RadioStationResponse;
import com.modzo.ors.web.web.api.radio.stations.RadioStationsClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
class RadioStationService {
    private final RadioStationsClient client;

    public RadioStationService(RadioStationsClient client) {
        this.client = client;
    }

    Data retrieve(Long id) {
        EntityModel<RadioStationResponse> station = client.getRadioStation(id);
        RadioStationResponse radioStation = station.getContent();
        return new Data(
                radioStation.getId(),
                radioStation.getUniqueId(),
                radioStation.getTitle(),
                radioStation.getWebsite()
        );
    }

    public static class Data {

        private final long id;

        private final String uniqueId;

        private final String title;

        private final String website;

        public Data(long id, String uniqueId, String title, String website) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.title = title;
            this.website = website;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getTitle() {
            return title;
        }

        public String getWebsite() {
            return website;
        }
    }
}
