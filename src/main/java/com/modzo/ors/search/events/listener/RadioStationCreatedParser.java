package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class RadioStationCreatedParser implements EventParser {

    private final RadioStationsRepository radioStationsDocumentRepository;

    private final RadioStations radioStations;

    public RadioStationCreatedParser(RadioStationsRepository radioStationsDocumentRepository,
                                     RadioStations radioStations) {
        this.radioStationsDocumentRepository = radioStationsDocumentRepository;
        this.radioStations = radioStations;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.RADIO_STATION;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.CREATED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        RadioStation savedRadioStation = radioStations.findById(domainEvent.getId()).get();
        RadioStationDocument radioStationDocument = new RadioStationDocument(
                savedRadioStation.getId(),
                savedRadioStation.getUniqueId(),
                savedRadioStation.getTitle(),
                savedRadioStation.isEnabled()
        );
        radioStationsDocumentRepository.save(radioStationDocument);
    }

}
