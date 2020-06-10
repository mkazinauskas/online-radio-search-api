package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RadioStationRefreshedParser implements EventParser {

    private final RadioStationsRepository radioStationsDocumentRepository;

    private final RadioStations radioStations;

    public RadioStationRefreshedParser(RadioStationsRepository radioStationsDocumentRepository,
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
        return StationsDomainEvent.Action.REFRESHED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        RadioStation savedRadioStation = radioStations.findById(domainEvent.getId()).get();

        Optional<RadioStationDocument> document = radioStationsDocumentRepository.findById(domainEvent.getId());
        if (document.isPresent()) {
            RadioStationDocument existingDocument = document.get();
            existingDocument.setTitle(savedRadioStation.getTitle());
            existingDocument.setEnabled(savedRadioStation.isEnabled());
            radioStationsDocumentRepository.save(existingDocument);
        } else {
            RadioStationDocument newDocument = new RadioStationDocument(
                    savedRadioStation.getId(),
                    savedRadioStation.getUniqueId(),
                    savedRadioStation.getTitle(),
                    savedRadioStation.isEnabled()
            );
            radioStationsDocumentRepository.save(newDocument);
        }
    }

}
