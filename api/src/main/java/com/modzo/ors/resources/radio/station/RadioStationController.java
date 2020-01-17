package com.modzo.ors.resources.radio.station;

import com.modzo.ors.domain.radio.station.RadioStation;
import com.modzo.ors.domain.radio.station.commands.GetRadioStation;
import com.modzo.ors.domain.radio.station.commands.GetRadioStations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.modzo.ors.resources.radio.station.RadioStationResource.create;
import static com.modzo.ors.resources.radio.station.RadioStationsResource.create;
import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationController {

    private final GetRadioStation.Handler radioStationHandler;

    private final GetRadioStations.Handler radioStationsHandler;

    public RadioStationController(GetRadioStation.Handler radioStationHandler,
                                  GetRadioStations.Handler radioStationsHandler) {
        this.radioStationHandler = radioStationHandler;
        this.radioStationsHandler = radioStationsHandler;
    }

    @GetMapping("/radio-stations")
    ResponseEntity<PagedResources<RadioStationResource>> getRadioStations(Pageable pageable) {
        Page<RadioStation> foundRadioStation = radioStationsHandler.handle(new GetRadioStations(pageable));
        return ok(create(foundRadioStation, pageable));
    }

    @GetMapping("/radio-stations/{id}")
    ResponseEntity<RadioStationResource> getRadioStation(@PathVariable("id") long id) {
        RadioStation foundRadioStation = radioStationHandler.handle(new GetRadioStation(id));
        return ok(create(foundRadioStation));
    }
}