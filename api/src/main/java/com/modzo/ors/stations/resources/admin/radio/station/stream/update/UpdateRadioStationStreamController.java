package com.modzo.ors.stations.resources.admin.radio.station.stream.update;

import com.modzo.ors.stations.domain.radio.station.stream.commands.UpdateRadioStationStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.accepted;

@RestController
class UpdateRadioStationStreamController {

    private final UpdateRadioStationStream.Handler updateRadioStationStream;

    public UpdateRadioStationStreamController(UpdateRadioStationStream.Handler updateRadioStationStream) {
        this.updateRadioStationStream = updateRadioStationStream;
    }

    @PatchMapping("/admin/radio-stations/{id}/streams/{streamId}")
    ResponseEntity<String> updateRadioStationStream(@PathVariable("id") long radioStationId,
                                                    @PathVariable("streamId") long streamId,
                                                    @Valid @RequestBody UpdateRadioStationStreamRequest request) {
        updateRadioStationStream.handle(
                new UpdateRadioStationStream(
                        radioStationId,
                        streamId,
                        new UpdateRadioStationStream.DataBuilder()
                                .setUrl(request.getUrl())
                                .setBitRate(request.getBitRate())
                                .setType(request.getType())
                                .setWorking(request.isWorking())
                                .build()
                )
        );
        return accepted()
                .build();
    }
}
