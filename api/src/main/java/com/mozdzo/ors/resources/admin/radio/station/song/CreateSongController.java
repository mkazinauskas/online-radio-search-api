package com.mozdzo.ors.resources.admin.radio.station.song;

import com.mozdzo.ors.domain.radio.station.song.commands.CreateSong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/admin/radio-stations/{id}/songs")
    ResponseEntity createRadioStation(@PathVariable("id") long radioStationId,
                                      @Valid @RequestBody CreateSongRequest request) {
        CreateSong.Result result = createSongHandler.handle(
                new CreateSong(radioStationId, request.getTitle(), request.getPlayedTime())
        );
        return created(create(format("/radio-stations/%s/songs/%s", radioStationId, result.id)))
                .build();
    }
}
