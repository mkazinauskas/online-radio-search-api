package com.modzo.ors.stations.resources.admin.song.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

class CreateSongRequest {

    @NotBlank
    private final String title;

    @JsonCreator
    CreateSongRequest(@JsonProperty("title") String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
