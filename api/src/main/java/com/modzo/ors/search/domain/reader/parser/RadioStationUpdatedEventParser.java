package com.modzo.ors.search.domain.reader.parser;

import com.modzo.ors.stations.domain.events.DomainEvent;
import com.modzo.ors.stations.domain.events.Event;
import com.modzo.ors.stations.domain.events.RadioStationUpdated;
import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.RadioStationsRepository;
import com.modzo.ors.search.domain.commands.FindGenreByUniqueId;
import com.modzo.ors.search.domain.commands.FindRadioStationByUniqueId;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class RadioStationUpdatedEventParser implements EventParser {

    private final FindRadioStationByUniqueId.Handler findRadioStationByUniqueId;

    private final FindGenreByUniqueId.Handler findGenreByUniqueId;

    private final RadioStationsRepository radioStationsRepository;

    public RadioStationUpdatedEventParser(
            FindRadioStationByUniqueId.Handler findRadioStationByUniqueId,
            FindGenreByUniqueId.Handler findGenreByUniqueId,
            RadioStationsRepository radioStationsRepository) {
        this.findRadioStationByUniqueId = findRadioStationByUniqueId;
        this.findGenreByUniqueId = findGenreByUniqueId;
        this.radioStationsRepository = radioStationsRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.RADIO_STATION_UPDATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        RadioStationUpdated.Data data = RadioStationUpdated.Data.deserialize(event.getBody());

        RadioStationDocument radioStationDocument = findRadioStationByUniqueId.handle(
                new FindRadioStationByUniqueId(data.getUniqueId())
        );

        radioStationDocument.setTitle(data.getTitle());
        radioStationDocument.setWebsite(data.getWebsite());
        radioStationDocument.setGenres(genres(data.getGenres()));
        radioStationsRepository.save(radioStationDocument);
    }

    private Set<GenreDocument> genres(Set<RadioStationUpdated.Data.Genre> genres) {
        return genres.stream()
                .map(this::findGenres)
                .collect(Collectors.toSet());
    }

    private GenreDocument findGenres(RadioStationUpdated.Data.Genre genre) {
        return findGenreByUniqueId.handle(new FindGenreByUniqueId(genre.getUniqueId()));
    }
}
