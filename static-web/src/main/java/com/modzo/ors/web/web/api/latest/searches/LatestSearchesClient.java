package com.modzo.ors.web.web.api.latest.searches;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "latestSearchesClient", url = "${application.apiUrl}")
public interface LatestSearchesClient {

    @GetMapping("/last-searches?size=${application.latestSearchesClient.size:1}")
    PagedModel<EntityModel<LastSearchResponse>> getLatestSearches();
}