package com.mozdzo.ors.search.parsedevents;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ParsedEvents extends ElasticsearchRepository<ParsedEvent, String> {
}
