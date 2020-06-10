package com.modzo.ors.search.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GenresRepository extends ElasticsearchRepository<GenreDocument, Long> {

}