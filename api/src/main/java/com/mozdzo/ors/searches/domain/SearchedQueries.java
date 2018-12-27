package com.mozdzo.ors.searches.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchedQueries extends ElasticsearchRepository<SearchedQuery, String> {
}
