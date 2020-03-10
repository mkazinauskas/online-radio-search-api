package com.modzo.ors.web.api.genres;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "genresClient", url = "${application.apiUrl}")
public interface GenresClient {

    @GetMapping("/genres/{genreId}")
    EntityModel<GenreResponse> getByGenreId(@PathVariable("genreId") long genreId);
}