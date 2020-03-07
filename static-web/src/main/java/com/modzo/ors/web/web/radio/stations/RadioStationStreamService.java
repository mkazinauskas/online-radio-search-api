package com.modzo.ors.web.web.radio.stations;

import com.modzo.ors.web.web.api.radio.stations.streams.RadioStationStreamResponse;
import com.modzo.ors.web.web.api.radio.stations.streams.RadioStationStreamsClient;
import com.modzo.ors.web.web.components.common.Paged;
import com.modzo.ors.web.web.components.common.model.RadioStationStreamModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class RadioStationStreamService {

    private final RadioStationStreamsClient client;

    public RadioStationStreamService(RadioStationStreamsClient client) {
        this.client = client;
    }

    Paged<RadioStationStreamModel> retrieve(Long id) {
        PagedModel<EntityModel<RadioStationStreamResponse>> streams = client.getRadioStationStreams(id);
        var content = streams.getContent()
                .stream()
                .map(EntityModel::getContent)
                .map(RadioStationStreamModel::new)
                .collect(Collectors.toList());
        return new Paged<>(
                content,
                streams.getMetadata()
        );
    }
}
