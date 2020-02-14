package com.modzo.ors.resources.admin.song.delte;

import com.modzo.ors.domain.radio.station.commands.DeleteRadioStation;
import com.modzo.ors.domain.radio.station.song.commands.DeleteSong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteSongController {

    private final DeleteSong.Handler deleteSong;

    public DeleteSongController(DeleteSong.Handler deleteSong) {
        this.deleteSong = deleteSong;
    }

    @DeleteMapping("/admin/songs/{id}")
    ResponseEntity delteSong(@PathVariable("id") long id) {
        deleteSong.handle(new DeleteSong(id));
        return ResponseEntity.ok().build();
    }
}
