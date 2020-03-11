package com.modzo.ors.stations.resources.admin.radio.station.update;

import com.modzo.ors.stations.domain.radio.station.commands.UpdateRadioStation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
class UpdateRadioStationController {

    private final UpdateRadioStation.Handler updateRadioStationHandler;

    public UpdateRadioStationController(UpdateRadioStation.Handler updateRadioStationHandler) {
        this.updateRadioStationHandler = updateRadioStationHandler;
    }

    @PatchMapping("/admin/radio-stations/{radioStationId}")
    ResponseEntity<String> updateRadioStation(
            @PathVariable("radioStationId") long radioStationId,
            @Valid @RequestBody UpdateRadioStationRequest request) {

        Set<UpdateRadioStation.Data.Genre> genres = request.getGenres().stream()
                .map(genre -> new UpdateRadioStation.Data.Genre(genre.getId()))
                .collect(Collectors.toSet());

        UpdateRadioStation.Data data = new UpdateRadioStation.DataBuilder()
                .setTitle(request.getTitle())
                .setWebsite(request.getWebsite())
                .setEnabled(request.isEnabled())
                .setGenres(genres)
                .build();

        updateRadioStationHandler.handle(new UpdateRadioStation(radioStationId, data));
        return ResponseEntity.accepted().build();
    }
}