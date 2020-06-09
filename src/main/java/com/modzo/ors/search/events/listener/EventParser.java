package com.modzo.ors.search.events.listener;

import com.modzo.ors.stations.events.StationsDomainEvent;

interface EventParser {

    StationsDomainEvent.Type type();

    StationsDomainEvent.Action action();

    void process(StationsDomainEvent domainEvent);
}
