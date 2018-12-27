package com.mozdzo.ors.search.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface GenresRepository extends ElasticsearchRepository<GenreDocument, String> {

    Optional<GenreDocument> findByUniqueId(String uniqueId);

}
