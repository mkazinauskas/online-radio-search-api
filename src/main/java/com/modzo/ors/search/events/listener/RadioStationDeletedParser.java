package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class RadioStationDeletedParser implements EventParser {

    private final RadioStationsRepository radioStationsDocumentRepository;

    public RadioStationDeletedParser(RadioStationsRepository radioStationsDocumentRepository) {
        this.radioStationsDocumentRepository = radioStationsDocumentRepository;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.RADIO_STATION;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.DELETED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        radioStationsDocumentRepository.deleteById(domainEvent.getId());
    }
}
