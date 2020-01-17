package com.modzo.ors.search.domain.parsedevents.commands;

import com.modzo.ors.search.domain.parsedevents.ParsedEvent;
import com.modzo.ors.search.domain.parsedevents.ParsedEvents;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class FindLastParsedEvent {

    @Component
    public static class Handler {
        private final ParsedEvents parsedEvents;

        public Handler(ParsedEvents parsedEvents) {
            this.parsedEvents = parsedEvents;
        }

        public Optional<ParsedEvent> handle() {
            NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(matchAllQuery())
                    .withPageable(PageRequest.of(0, 1));

            searchQuery.withSort(
                    SortBuilders.fieldSort("eventId")
                            .unmappedType("long")
                            .order(SortOrder.DESC)
            );
            Page<ParsedEvent> events = parsedEvents.search(searchQuery.build());
            if (events.hasContent()) {
                return Optional.of(events.getContent().get(0));
            } else {
                return Optional.empty();
            }
        }
    }
}
