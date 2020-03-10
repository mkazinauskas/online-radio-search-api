package com.modzo.ors.web.search.genre.radio.stations;

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
class RadioStationByGenreService {

    private final RadioStationsClient radioStationsClient;

    public RadioStationByGenreService(RadioStationsClient radioStationsClient) {
        this.radioStationsClient = radioStationsClient;
    }

    Paged<RadioStationModel> retrieveStationBy(long songId, Pageable pageable) {
        var radioStationByGenre = radioStationsClient.getRadioStationByGenreId(
                songId,
                pageable.getPageNumber()
        );
        PagedModel.PageMetadata metadata = radioStationByGenre.getMetadata();
        var content = radioStationByGenre.getContent().stream()
                .map(EntityModel::getContent)
                .filter(Objects::nonNull)
                .map(RadioStationModel::new)
                .collect(Collectors.toList());

        return new Paged<>(content, metadata);
    }
}
