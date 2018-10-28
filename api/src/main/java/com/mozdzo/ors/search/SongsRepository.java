package com.mozdzo.ors.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface SongsRepository extends ElasticsearchRepository<SongDocument, String> {

    Optional<SongDocument> findByUniqueId(String uniqueId);

}
