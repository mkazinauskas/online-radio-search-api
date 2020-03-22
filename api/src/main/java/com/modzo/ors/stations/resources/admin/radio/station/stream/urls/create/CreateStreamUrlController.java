package com.modzo.ors.stations.resources.admin.radio.station.stream.urls.create;

import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStreamUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;

@RestController
class CreateStreamUrlController {

    private final CreateRadioStationStreamUrl.Handler createRadioStationStreamUrlHandler;

    public CreateStreamUrlController(CreateRadioStationStreamUrl.Handler createRadioStationStreamUrlHandler) {
        this.createRadioStationStreamUrlHandler = createRadioStationStreamUrlHandler;
    }

    @PostMapping("/admin/radio-stations/{radioStationId}/streams/{streamId}/urls")
    ResponseEntity<String> createRadioStationStreamUrl(@PathVariable("radioStationId") long radioStationId,
                                                       @PathVariable("streamId") long streamId,
                                                       @Validated @RequestBody CreateStreamUrlRequest request) {
        CreateRadioStationStreamUrl.Result result = createRadioStationStreamUrlHandler.handle(
                new CreateRadioStationStreamUrl(radioStationId, streamId, request.getType(), request.getUrl())
        );
        return created(create(format("/radio-stations/%s/streams/%s", radioStationId, result.id)))
                .build();
    }
}
