package com.modzo.ors.web.search.song.radio.stations;

import com.modzo.ors.web.api.radio.stations.RadioStationsClient;
import com.modzo.ors.web.components.common.Paged;
import com.modzo.ors.web.components.common.model.RadioStationModel;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
class RadioStationBySongService {

    private final RadioStationsClient radioStationsClient;

    public RadioStationBySongService(RadioStationsClient radioStationsClient) {
        this.radioStationsClient = radioStationsClient;
    }

    Paged<RadioStationModel> retrieveStationBy(long songId, Pageable pageable) {
        var radioStationByPlayedSongId = radioStationsClient.getRadioStationByPlayedSongId(
                songId,
                pageable.getPageNumber()
        );
        PagedModel.PageMetadata metadata = radioStationByPlayedSongId.getMetadata();
        var content = radioStationByPlayedSongId.getContent().stream()
                .map(EntityModel::getContent)
                .filter(Objects::nonNull)
                .map(RadioStationModel::new)
                .collect(Collectors.toList());

        return new Paged<>(content, metadata);
    }
}
