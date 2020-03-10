package com.modzo.ors.web.search.radio.station;

import com.modzo.ors.web.api.search.radio.station.SearchRadioStationClient;
import com.modzo.ors.web.components.common.Paged;
import com.modzo.ors.web.components.common.model.RadioStationModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class SearchByRadioStationService {

    private final SearchRadioStationClient searchRadioStationClient;

    SearchByRadioStationService(SearchRadioStationClient searchRadioStationClient) {
        this.searchRadioStationClient = searchRadioStationClient;
    }

    Paged<RadioStationModel> retrieve(String title, Pageable pageable) {
        var response = searchRadioStationClient.searchRadioStationByTitle(title, pageable.getPageNumber());
        var content = response.getContent().stream()
                .map(RadioStationModel::new)
                .collect(Collectors.toList());

        return new Paged<>(content, response.getMetadata());
    }
}
