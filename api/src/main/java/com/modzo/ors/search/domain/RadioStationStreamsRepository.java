package com.modzo.ors.search.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface RadioStationStreamsRepository extends ElasticsearchRepository<RadioStationStreamDocument, String> {

    Optional<RadioStationStreamDocument> findByUniqueId(String uniqueId);

}