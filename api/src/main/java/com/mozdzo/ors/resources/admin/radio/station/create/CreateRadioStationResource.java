package com.mozdzo.ors.resources.admin.radio.station.create;

import com.mozdzo.ors.domain.radio.station.commands.CreateRadioStation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
class CreateRadioStationResource {

    private final CreateRadioStation.Handler createStationHandler;

    public CreateRadioStationResource(CreateRadioStation.Handler createStationHandler) {
        this.createStationHandler = createStationHandler;
    }

    @PostMapping("/admin/radio-stations")
    ResponseEntity createRadioStation(@Valid @RequestBody CreateRadioStationRequest request) {
        CreateRadioStation.Result result = createStationHandler.handle(new CreateRadioStation(request.getTitle()));
        return ResponseEntity.created(URI.create(String.format("/radio-stations/%s", result.id))).build();
    }
}
