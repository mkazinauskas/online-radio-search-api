package com.modzo.ors.web.api.search.radio.station;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchRadioStationClient", url = "${application.apiUrl}")
public interface SearchRadioStationClient {

    @GetMapping("/search/radio-station?size=${application.searchRadioStationClient.size:1}")
    PagedModel<SearchRadioStationResultResponse> searchRadioStationByTitle(
            @RequestParam("title") String title,
            @RequestParam("page") int page
    );
}