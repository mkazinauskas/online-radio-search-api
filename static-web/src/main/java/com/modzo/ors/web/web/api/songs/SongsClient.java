package com.modzo.ors.web.web.api.songs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "songsClient", url = "${application.apiUrl}")
public interface SongsClient {

    @GetMapping("/songs/{songId}")
    EntityModel<SongResponse> getSongById(@PathVariable("songId") long songId);
}