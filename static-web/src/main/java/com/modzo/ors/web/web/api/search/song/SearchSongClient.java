package com.modzo.ors.web.web.api.search.song;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchSongClient", url = "${application.apiUrl}")
public interface SearchSongClient {

    @GetMapping("/search/song?size=1")
    PagedModel<SearchSongResultResponse> searchSongsByTitle(@RequestParam("title") String title,
                                                            @RequestParam("page") int page);
}