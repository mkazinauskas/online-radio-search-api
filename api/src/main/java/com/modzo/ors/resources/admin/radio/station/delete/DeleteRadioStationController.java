package com.modzo.ors.resources.admin.radio.station.delete;

import com.modzo.ors.domain.radio.station.commands.DeleteRadioStation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteRadioStationController {

    private final DeleteRadioStation.Handler deleteRadioStation;

    public DeleteRadioStationController(DeleteRadioStation.Handler deleteRadioStation) {
        this.deleteRadioStation = deleteRadioStation;
    }

    @DeleteMapping("/admin/radio-stations/{id}")
    ResponseEntity deleteRadioStation(@PathVariable("id") long id) {
        deleteRadioStation.handle(new DeleteRadioStation(id));
        return ResponseEntity.ok().build();
    }
}
