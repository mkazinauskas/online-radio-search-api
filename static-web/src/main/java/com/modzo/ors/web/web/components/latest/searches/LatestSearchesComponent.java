package com.modzo.ors.web.web.components.latest.searches;

import com.modzo.ors.web.web.api.latest.searches.LastSearchResponse;
import com.modzo.ors.web.web.api.latest.searches.LatestSearchesClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LatestSearchesComponent {

    private final LatestSearchesClient latestSearchesClient;

    LatestSearchesComponent(LatestSearchesClient latestSearchesClient) {
        this.latestSearchesClient = latestSearchesClient;
    }

    public List<LatestSearchesData> retrieve() {
        PagedModel<EntityModel<LastSearchResponse>> latestSearches = latestSearchesClient.getLatestSearches();
        List<LatestSearchesData.Query> searches = latestSearches.getContent().stream()
                .map(EntityModel::getContent)
                .filter(Objects::nonNull)
                .map(search -> new LatestSearchesData.Query(search.getQuery()))
                .collect(Collectors.toList());
        return List.of(new LatestSearchesData(searches));
    }
}
