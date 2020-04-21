package com.modzo.ors.stations.resources.admin.song.create;

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
class CreateSongController {

    private final CreateSong.Handler createSongHandler;

    public CreateSongController(CreateSong.Handler createSongHandler) {
        this.createSongHandler = createSongHandler;
    }

    @PostMapping("/admin/songs")
    ResponseEntity createSong(@Valid @RequestBody CreateSongRequest request) {
        CreateSong.Result result = createSongHandler.handle(
                new CreateSong(request.getTitle())
        );
        return created(create(format("/songs/%s", result.id)))
                .build();
    }
}
