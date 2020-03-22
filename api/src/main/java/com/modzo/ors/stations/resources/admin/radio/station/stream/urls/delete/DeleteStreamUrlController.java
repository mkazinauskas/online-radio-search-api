package com.modzo.ors.stations.resources.admin.radio.station.stream.urls.delete;

import com.modzo.ors.stations.domain.radio.station.stream.commands.DeleteRadioStationStreamUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteStreamUrlController {

    private final DeleteRadioStationStreamUrl.Handler deleteRadioStationStreamUrl;

    public DeleteStreamUrlController(DeleteRadioStationStreamUrl.Handler deleteRadioStationStreamUrl) {
        this.deleteRadioStationStreamUrl = deleteRadioStationStreamUrl;
    }

    @DeleteMapping("/admin/radio-stations/{radioStationId}/streams/{streamId}/urls/{urlId}")
    ResponseEntity<String> deleteRadioStation(@PathVariable("radioStationId") long radioStationId,
                                              @PathVariable("streamId") long streamId,
                                              @PathVariable("urlId") long urlId) {
        deleteRadioStationStreamUrl.handle(new DeleteRadioStationStreamUrl(radioStationId, streamId, urlId));
        return ResponseEntity.ok().build();
    }
}
