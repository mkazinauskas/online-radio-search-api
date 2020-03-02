package com.modzo.ors.web.web.api.search.song;

import com.modzo.ors.web.web.api.radio.stations.RadioStationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchSongClient", url = "${application.apiUrl}")
public interface SearchSongClient {

    @GetMapping("/search/song?size=10")
    PagedModel<SearchSongResultResponse> searchSongsByTitle(@RequestParam("title") String title);
}