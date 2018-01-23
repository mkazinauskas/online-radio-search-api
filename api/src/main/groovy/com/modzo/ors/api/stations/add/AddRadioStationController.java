package com.modzo.ors.api.stations.add;

import com.modzo.ors.domain.commands.add.AddRadioStation;
import com.modzo.ors.domain.commands.add.AddRadioStationHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
class AddRadioStationController {

    private final AddRadioStationHandler handler;

    public AddRadioStationController(AddRadioStationHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/stations")
    ResponseEntity add(@RequestBody @Valid AddRadioStationRequest request) {
        handler.handle(new AddRadioStation(request.getUrl(), request.getName()));
        return ResponseEntity.created(URI.create("/stations")).build();
    }

}
