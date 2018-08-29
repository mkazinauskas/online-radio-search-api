package com.mozdzo.ors.resources.radio.station;

import com.mozdzo.ors.domain.radio.station.RadioStation;
import com.mozdzo.ors.domain.radio.station.commands.GetRadioStation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RadioStationController {

    private final GetRadioStation.Handler radioStationHandler;

    RadioStationController(GetRadioStation.Handler radioStationHandler) {
        this.radioStationHandler = radioStationHandler;
    }

    @GetMapping("/radio-stations/{id}")
    ResponseEntity<RadioStationResource> getRadioStation(@PathVariable("id") long id) {
        RadioStation foundRadioStation = radioStationHandler.handle(new GetRadioStation(id));
        return ResponseEntity.ok(RadioStationResource.create(foundRadioStation));
        //https://lankydanblog.com/2017/09/10/applying-hateoas-to-a-rest-api-with-spring-boot/
    }
}