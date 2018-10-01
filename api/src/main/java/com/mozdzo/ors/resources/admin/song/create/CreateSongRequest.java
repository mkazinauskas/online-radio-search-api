package com.mozdzo.ors.resources.admin.song.create;

import javax.validation.constraints.NotBlank;

public class CreateSongRequest {
    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
