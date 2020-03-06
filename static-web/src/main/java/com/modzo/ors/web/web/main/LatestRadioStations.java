package com.modzo.ors.web.web.main;

import com.modzo.ors.web.web.api.radio.stations.RadioStationResponse;
import com.modzo.ors.web.web.api.radio.stations.RadioStationsClient;
import com.modzo.ors.web.web.components.common.Paged;
import com.modzo.ors.web.web.components.common.model.RadioStationModel;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class LatestRadioStations {

    private final RadioStationsClient client;

    LatestRadioStations(RadioStationsClient client) {
        this.client = client;
    }

    Paged<RadioStationModel> retrieve(Pageable pageable) {
        PagedModel<EntityModel<RadioStationResponse>> stations = client.getRadioStations(pageable.getPageNumber());
        List<RadioStationModel> content = stations.getContent()
                .stream()
                .map(EntityModel::getContent)
                .map(RadioStationModel::new)
                .collect(Collectors.toList());

        return new Paged<>(
                content,
                stations.getMetadata()
        );
    }
}
