package com.mozdzo.ors.searches.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface SearchedQueries extends ElasticsearchRepository<SearchedQuery, String> {

    Optional<SearchedQuery> findByUniqueId(String uniqueId);
}
