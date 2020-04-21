package com.modzo.ors.stations.resources.admin.radio.station.stream.create;

import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStream;
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
class CreateRadioStationStreamController {

    private final CreateRadioStationStream.Handler createStationStreamHandler;

    public CreateRadioStationStreamController(CreateRadioStationStream.Handler createStationStreamHandler) {
        this.createStationStreamHandler = createStationStreamHandler;
    }

    @PostMapping("/admin/radio-stations/{id}/streams")
    ResponseEntity<String> createRadioStationStream(@PathVariable("id") long radioStationId,
                                            @Validated @RequestBody CreateRadioStationStreamRequest request) {
        CreateRadioStationStream.Result result = createStationStreamHandler.handle(
                new CreateRadioStationStream(radioStationId, request.getUrl())
        );
        return created(create(format("/radio-stations/%s/streams/%s", radioStationId, result.id)))
                .build();
    }
}
