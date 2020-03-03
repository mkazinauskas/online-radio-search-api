package com.modzo.ors.stations.resources.radio.station;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStationBySongId;
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.modzo.ors.stations.resources.radio.station.RadioStationModel.create;
import static com.modzo.ors.stations.resources.radio.station.RadioStationsModel.create;
import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationController {

    private final GetRadioStation.Handler radioStationHandler;

    private final GetRadioStations.Handler radioStationsHandler;

    private final GetRadioStationBySongId.Handler radioStationBySongIdHandler;

    public RadioStationController(GetRadioStation.Handler radioStationHandler,
                                  GetRadioStations.Handler radioStationsHandler,
                                  GetRadioStationBySongId.Handler radioStationBySongIdHandler) {
        this.radioStationHandler = radioStationHandler;
        this.radioStationsHandler = radioStationsHandler;
        this.radioStationBySongIdHandler = radioStationBySongIdHandler;
    }

    @GetMapping("/radio-stations")
    ResponseEntity<RadioStationsModel> getRadioStations(Pageable pageable) {
        Page<RadioStation> foundRadioStation = radioStationsHandler.handle(new GetRadioStations(pageable));
        return ok(create(foundRadioStation, pageable));
    }

    @GetMapping("/radio-stations/{id}")
    ResponseEntity<RadioStationModel> getRadioStation(@PathVariable("id") long id) {
        RadioStation foundRadioStation = radioStationHandler.handle(new GetRadioStation(id));
        return ok(create(foundRadioStation));
    }

    @GetMapping(value = "/radio-stations", params = {"songId"})
    ResponseEntity<RadioStationsModel> getRadioStationBySongId(
            @RequestParam("songId") long songId,
            Pageable pageable
    ) {
        Page<RadioStation> foundRadioStation = radioStationBySongIdHandler.handle(
                new GetRadioStationBySongId(songId, pageable)
        );
        return ok(create(foundRadioStation, pageable));
    }
}