package com.mozdzo.ors.search.domain.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;

public interface EventParser {

    Class<? extends DomainEvent.Data> eventClass();

    void parse(Event event);
}
