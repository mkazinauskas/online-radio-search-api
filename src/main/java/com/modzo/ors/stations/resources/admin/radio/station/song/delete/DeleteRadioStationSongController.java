package com.modzo.ors.stations.resources.admin.radio.station.song.delete;

import com.modzo.ors.stations.domain.radio.station.song.commands.DeleteRadioStationSong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteRadioStationSongController {

    private final DeleteRadioStationSong.Handler deleteRadioStationSong;

    public DeleteRadioStationSongController(DeleteRadioStationSong.Handler deleteRadioStationSong) {
        this.deleteRadioStationSong = deleteRadioStationSong;
    }

    @DeleteMapping("/admin/radio-stations/{radioStationId}/songs/{songId}")
    ResponseEntity deleteRadioStationSong(@PathVariable("radioStationId") long radioStationId,
                                          @PathVariable("songId") long songId) {
        deleteRadioStationSong.handle(new DeleteRadioStationSong(radioStationId, songId));
        return ResponseEntity.ok().build();
    }
}
