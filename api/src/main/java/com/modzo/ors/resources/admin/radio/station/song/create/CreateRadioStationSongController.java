package com.modzo.ors.resources.admin.radio.station.song.create;

import com.modzo.ors.domain.radio.station.song.commands.CreateRadioStationSong;
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
class CreateRadioStationSongController {

    private final CreateRadioStationSong.Handler createRadioStationSong;

    public CreateRadioStationSongController(CreateRadioStationSong.Handler createRadioStationSong) {
        this.createRadioStationSong = createRadioStationSong;
    }

    @PostMapping("/admin/radio-stations/{id}/songs")
    ResponseEntity createSong(@PathVariable("id") long radioStationId,
                              @Valid @RequestBody CreateRadioStationSongRequest request) {
        CreateRadioStationSong.Result result = createRadioStationSong.handle(
                new CreateRadioStationSong(request.getSongId(), radioStationId, request.getPlayedTime())
        );
        return created(create(format("/radio-stations/%s/songs/%s", radioStationId, result.id)))
                .build();
    }
}
