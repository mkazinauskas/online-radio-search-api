package com.modzo.ors.stations.resources.admin.genre.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

class CreateGenreRequest {

    @NotBlank
    private final String title;

    @JsonCreator
    CreateGenreRequest(@JsonProperty("title") String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
