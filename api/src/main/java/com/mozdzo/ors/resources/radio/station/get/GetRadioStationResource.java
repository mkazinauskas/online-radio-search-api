package com.mozdzo.ors.resources.radio.station.get;

import com.mozdzo.ors.domain.radio.station.RadioStation;
import com.mozdzo.ors.domain.radio.station.commands.GetRadioStation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetRadioStationResource {

    private final GetRadioStation.Handler radioStationHandler;

    GetRadioStationResource(GetRadioStation.Handler radioStationHandler) {
        this.radioStationHandler = radioStationHandler;
    }

    @GetMapping("/radio-stations/{id}")
    ResponseEntity<GetRadioStationResponse> getRadioStation(@PathVariable("id") long id) {
        RadioStation foundRadioStation = radioStationHandler.handle(new GetRadioStation(id));
        return ResponseEntity.ok(new GetRadioStationResponse(foundRadioStation));
    }
}