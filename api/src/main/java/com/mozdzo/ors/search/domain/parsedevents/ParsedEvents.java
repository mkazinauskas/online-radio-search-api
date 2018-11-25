package com.mozdzo.ors.search.domain.parsedevents;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ParsedEvents extends ElasticsearchRepository<ParsedEvent, String> {
}
