package com.modzo.ors.stations.resources.admin.genre.create;

import com.modzo.ors.stations.domain.radio.station.genre.commands.CreateGenre;
import com.modzo.ors.stations.domain.song.commands.CreateSong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;
import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.created;

@RestController
class CreateGenreController {

    private final CreateGenre.Handler createGenreHandler;

    public CreateGenreController(CreateGenre.Handler createGenreHandler) {
        this.createGenreHandler = createGenreHandler;
    }

    @PostMapping("/admin/genres")
    ResponseEntity<String> createGenre(@Valid @RequestBody CreateGenreRequest request) {
        CreateGenre.Result result = createGenreHandler.handle(
                new CreateGenre(request.getTitle())
        );
        return created(create(format("/genres/%s", result.id)))
                .build();
    }
}
