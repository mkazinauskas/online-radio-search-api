package com.modzo.ors.web.api.search.genre;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchGenreClient", url = "${application.apiUrl}")
public interface SearchGenreClient {

    @GetMapping("/search/genre?size=${application.searchGenreClient.size:1}")
    PagedModel<SearchGenreResultResponse> searchGenresByTitle(@RequestParam("title") String title,
                                                              @RequestParam("page") int page);
}