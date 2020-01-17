package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.domain.events.DomainEvent;
import com.modzo.ors.domain.events.Event;

public interface EventParser {

    Class<? extends DomainEvent.Data> eventClass();

    void parse(Event event);
}