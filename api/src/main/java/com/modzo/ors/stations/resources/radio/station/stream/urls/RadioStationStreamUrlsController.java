package com.modzo.ors.stations.resources.radio.station.stream.urls;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStreamUrls;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationStreamUrlsController {

    private final GetRadioStationStreamUrls.Handler getRadioStationStreamUrlsHandler;

    private final GetRadioStationStreamUrl.Handler getRadioStationStreamUrlHandler;

    public RadioStationStreamUrlsController(GetRadioStationStreamUrls.Handler getRadioStationStreamUrlsHandler,
                                            GetRadioStationStreamUrl.Handler getRadioStationStreamUrlHandler) {
        this.getRadioStationStreamUrlsHandler = getRadioStationStreamUrlsHandler;
        this.getRadioStationStreamUrlHandler = getRadioStationStreamUrlHandler;
    }

    @GetMapping("/radio-stations/{radioStationId}/streams/{streamId}/urls")
    ResponseEntity<RadioStationStreamUrlsModel> getStreamUrls(
            @PathVariable("radioStationId") long radioStationId,
            @PathVariable("streamId") long streamId,
            Pageable pageable
    ) {
        Page<StreamUrl> urls = getRadioStationStreamUrlsHandler.handle(
                new GetRadioStationStreamUrls(streamId, pageable)
        );
        return ok(RadioStationStreamUrlsModel.create(urls, radioStationId, streamId, pageable));
    }

    @GetMapping("/radio-stations/{radioStationId}/streams/{streamId}/urls/{urlId}")
    ResponseEntity<RadioStationStreamUrlModel> getStreamUrl(
            @PathVariable("radioStationId") long radioStationId,
            @PathVariable("streamId") long streamId,
            @PathVariable("urlId") long urlId) {
        StreamUrl url = getRadioStationStreamUrlHandler.handle(
                new GetRadioStationStreamUrl(radioStationId, streamId, urlId)
        );
        return ok(RadioStationStreamUrlModel.create(radioStationId, streamId, url));
    }
}