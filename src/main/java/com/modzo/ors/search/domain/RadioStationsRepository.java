package com.modzo.ors.search.domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;
import java.util.UUID;

public interface RadioStationsRepository extends ElasticsearchRepository<RadioStationDocument, Long> {

    Optional<RadioStationDocument> findByUniqueId(UUID uniqueId);

}
