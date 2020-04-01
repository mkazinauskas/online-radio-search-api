package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.StreamUrlCheckedTimeUpdated;
import org.springframework.stereotype.Component;

@Component
class StreamUrlCheckedTimeUpdatedEventParser implements EventParser {

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.STREAM_URL_CHECKED_TIME_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        StreamUrlCheckedTimeUpdated.Data.deserialize(event.getBody());
        // event data is not needed
    }
}
