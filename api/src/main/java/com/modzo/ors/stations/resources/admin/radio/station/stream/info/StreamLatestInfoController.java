package com.modzo.ors.stations.resources.admin.radio.station.stream.info;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
class StreamLatestInfoController {

    private final LatestInfoService latestInfoService;

    public StreamLatestInfoController(LatestInfoService latestInfoService) {
        this.latestInfoService = latestInfoService;
    }

    @PostMapping("/admin/radio-stations/{radio-station-id}/streams/{stream-id}/latest-info")
    ResponseEntity latestInfo(@PathVariable("radio-station-id") long radioStationId,
                              @PathVariable("stream-id") long streamId) {
        latestInfoService.update(radioStationId, streamId);
        return noContent().build();
    }
}
