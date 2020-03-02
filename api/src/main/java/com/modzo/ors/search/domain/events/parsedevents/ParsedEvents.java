package com.modzo.ors.search.domain.events.parsedevents;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ParsedEvents extends ElasticsearchRepository<ParsedEvent, String> {
}
