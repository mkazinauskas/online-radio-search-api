package com.mozdzo.ors.resources.radio.station.stream;

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStreams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationStreamController {

    private final GetRadioStationStream.Handler radioStationStreamHandler;

    private final GetRadioStationStreams.Handler radioStationStreamsHandler;

    public RadioStationStreamController(GetRadioStationStream.Handler radioStationStreamHandler,
                                        GetRadioStationStreams.Handler radioStationStreamsHandler) {
        this.radioStationStreamHandler = radioStationStreamHandler;
        this.radioStationStreamsHandler = radioStationStreamsHandler;
    }

    @GetMapping("/radio-stations/{radioStationId}/streams")
    ResponseEntity<PagedResources<RadioStationStreamResource>> getRadioStationStreams(
            @PathVariable("radioStationId") long radioStationId,
            Pageable pageable
    ) {
        Page<RadioStationStream> foundStreams = radioStationStreamsHandler.handle(
                new GetRadioStationStreams(radioStationId, pageable)
        );
        return ok(RadioStationStreamsResource.create(foundStreams, radioStationId, pageable));
    }

    @GetMapping("/radio-stations/{radioStationId}/streams/{streamId}")
    ResponseEntity<RadioStationStreamResource> getRadioStationStream(@PathVariable("radioStationId") long radioStationId,
                                                                     @PathVariable("streamId") long streamId) {
        RadioStationStream foundStream = radioStationStreamHandler.handle(new GetRadioStationStream(radioStationId, streamId));
        return ok(RadioStationStreamResource.create(radioStationId, foundStream));
    }
}