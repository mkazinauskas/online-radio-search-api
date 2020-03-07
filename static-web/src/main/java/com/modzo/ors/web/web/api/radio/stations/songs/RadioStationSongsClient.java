package com.modzo.ors.web.web.api.radio.stations.songs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "radioStationSongsClient", url = "${application.apiUrl}")
public interface RadioStationSongsClient {

    @GetMapping(
            "/radio-stations/{radioStationId}/songs?size=${application.radioStationSongsClient.size:1}&sort=id,desc"
    )
    PagedModel<EntityModel<RadioStationSongResponse>> getRadioStationSongs(
            @PathVariable("radioStationId") long radioStationId,
            @RequestParam("page") long page
    );
}