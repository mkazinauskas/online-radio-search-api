package com.modzo.ors.stations.resources.admin.radio.station.stream.delete;

import com.modzo.ors.stations.domain.radio.station.stream.commands.DeleteRadioStationStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteRadioStationStreamController {

    private final DeleteRadioStationStream.Handler deleteRadioStationStream;

    public DeleteRadioStationStreamController(DeleteRadioStationStream.Handler deleteRadioStationStream) {
        this.deleteRadioStationStream = deleteRadioStationStream;
    }

    @DeleteMapping("/admin/radio-stations/{radioStationId}/streams/{streamId}")
    ResponseEntity<String> deleteRadioStationStream(@PathVariable("radioStationId") long radioStationId,
                                      @PathVariable("streamId") long streamId) {
        deleteRadioStationStream.handle(new DeleteRadioStationStream(radioStationId, streamId));
        return ResponseEntity.ok().build();
    }
}
