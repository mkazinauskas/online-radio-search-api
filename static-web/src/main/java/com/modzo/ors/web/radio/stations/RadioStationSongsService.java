package com.modzo.ors.web.radio.stations;

import com.modzo.ors.web.api.radio.stations.songs.RadioStationSongsClient;
import com.modzo.ors.web.components.common.Paged;
import com.modzo.ors.web.components.common.model.RadioStationSongModel;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class RadioStationSongsService {

    private final RadioStationSongsClient client;

    public RadioStationSongsService(RadioStationSongsClient client) {
        this.client = client;
    }

    Paged<RadioStationSongModel> retrieve(long radioStationId, Pageable pageable) {
        var songs = client.getRadioStationSongs(radioStationId, pageable.getPageNumber());
        var content = songs.getContent().stream()
                .map(EntityModel::getContent)
                .map(RadioStationSongModel::new)
                .collect(Collectors.toList());

        return new Paged<>(
                content,
                songs.getMetadata()
        );
    }
}