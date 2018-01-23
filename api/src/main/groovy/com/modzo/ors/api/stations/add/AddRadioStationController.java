package com.modzo.ors.api.stations.add;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
class AddRadioStationController {

    @PostMapping("/stations")
    ResponseEntity add(@RequestBody @Valid AddRadioStationRequest request) {
        return ResponseEntity.created(URI.create("/stations")).build();
    }

}
