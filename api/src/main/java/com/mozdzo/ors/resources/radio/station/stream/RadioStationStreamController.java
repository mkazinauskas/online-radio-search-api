package com.mozdzo.ors.resources.radio.station.stream;

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationStreamController {

    private final GetRadioStationStream.Handler handler;

    RadioStationStreamController(GetRadioStationStream.Handler handler) {
        this.handler = handler;
    }

    @GetMapping("/radio-stations/{radioStationId}/streams/{streamId}")
    ResponseEntity<RadioStationStreamResource> getRadioStationStream(@PathVariable("radioStationId") long radioStationId,
                                                                     @PathVariable("streamId") long streamId) {
        RadioStationStream foundStream = handler.handle(new GetRadioStationStream(radioStationId, streamId));
        return ok(RadioStationStreamResource.create(radioStationId, foundStream));
    }
}