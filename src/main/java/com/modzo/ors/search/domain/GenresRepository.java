package com.modzo.ors.search.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface GenresRepository extends ElasticsearchRepository<GenreDocument, Long> {

    Optional<GenreDocument> findByUniqueId(String uniqueId);

    void deleteByUniqueId(String uniqueId);

}
