package com.mozdzo.ors.search.parsedevents;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ParsedEvents extends ElasticsearchRepository<ParsedEvent, String> {
    Optional<ParsedEvent> findByEventId(String eventId);
}
