package com.modzo.ors.search.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface SongsRepository extends ElasticsearchRepository<SongDocument, Long> {

    Optional<SongDocument> findByUniqueId(String uniqueId);

    void deleteByUniqueId(String uniqueId);

}