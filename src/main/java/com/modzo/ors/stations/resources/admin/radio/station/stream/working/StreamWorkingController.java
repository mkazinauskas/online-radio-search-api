package com.modzo.ors.stations.resources.admin.radio.station.stream.working;

import com.modzo.ors.stations.services.stream.checker.StreamCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
class StreamWorkingController {

    private final StreamCheckService streamCheckService;

    public StreamWorkingController(StreamCheckService streamCheckService) {
        this.streamCheckService = streamCheckService;
    }

    @PostMapping("/admin/radio-stations/{radio-station-id}/streams/{stream-id}/working")
    ResponseEntity<String> working(@PathVariable("radio-station-id") long radioStationId,
                                   @PathVariable("stream-id") long streamId) {
        streamCheckService.checkIfStreamWorks(radioStationId, streamId);
        return noContent().build();
    }
}
