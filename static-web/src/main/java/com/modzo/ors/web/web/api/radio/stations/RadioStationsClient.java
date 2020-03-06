package com.modzo.ors.web.web.api.radio.stations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "radioStationsClient", url = "${application.apiUrl}")
public interface RadioStationsClient {

    @GetMapping("/radio-stations?size=100")
    PagedModel<EntityModel<RadioStationResponse>> getRadioStations();

    @GetMapping("/radio-stations/{id}?size=100")
    EntityModel<RadioStationResponse> getRadioStation(@PathVariable("id") long id);

    @GetMapping("/radio-stations?size=10")
    PagedModel<EntityModel<RadioStationResponse>> getRadioStationByPlayedSongId(
            @RequestParam("songId") long songId,
            @RequestParam("page") long page
    );
}