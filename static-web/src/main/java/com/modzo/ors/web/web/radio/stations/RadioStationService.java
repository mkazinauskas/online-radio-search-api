package com.modzo.ors.web.web.radio.stations;

import com.modzo.ors.web.web.api.radio.stations.RadioStationResponse;
import com.modzo.ors.web.web.api.radio.stations.RadioStationsClient;
import com.modzo.ors.web.web.components.common.model.RadioStationModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
class RadioStationService {

    private final RadioStationsClient client;

    public RadioStationService(RadioStationsClient client) {
        this.client = client;
    }

    RadioStationModel retrieve(Long id) {
        EntityModel<RadioStationResponse> station = client.getRadioStation(id);
        RadioStationResponse radioStation = station.getContent();
        return new RadioStationModel(
                radioStation.getId(),
                radioStation.getUniqueId(),
                radioStation.getTitle(),
                radioStation.getWebsite(),
                null
        );
    }
}