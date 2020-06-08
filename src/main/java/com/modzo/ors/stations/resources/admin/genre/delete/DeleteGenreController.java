package com.modzo.ors.stations.resources.admin.genre.delete;

import com.modzo.ors.stations.domain.radio.station.genre.commands.DeleteGenre;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteGenreController {

    private final DeleteGenre.Handler deleteGenre;

    public DeleteGenreController(DeleteGenre.Handler deleteGenre) {
        this.deleteGenre = deleteGenre;
    }

    @DeleteMapping("/admin/genres/{id}")
    ResponseEntity<String> deleteSong(@PathVariable("id") long id) {
        deleteGenre.handle(new DeleteGenre(id));
        return ResponseEntity.ok().build();
    }

}
