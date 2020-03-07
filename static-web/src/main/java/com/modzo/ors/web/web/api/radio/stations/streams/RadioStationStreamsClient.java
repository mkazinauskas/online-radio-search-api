package com.modzo.ors.web.web.api.radio.stations.streams;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "radioStreamsClient", url = "${application.apiUrl}")
public interface RadioStationStreamsClient {

    @GetMapping("/radio-stations/{id}/streams?size=${application.radioStreamsClient.size:1}")
    PagedModel<EntityModel<RadioStationStreamResponse>> getRadioStationStreams(@PathVariable("id") long id);

    @GetMapping("/radio-stations/{radioStationId}/streams/{streamId}")
    EntityModel<RadioStationStreamResponse> getRadioStationStream(
            @PathVariable("radioStationId") long radioStationId,
            @PathVariable("streamId") long streamId
    );
}
