package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;

public interface EventParser {

    Class<? extends DomainEvent.Data> eventClass();

    void parse(Event event);
}
