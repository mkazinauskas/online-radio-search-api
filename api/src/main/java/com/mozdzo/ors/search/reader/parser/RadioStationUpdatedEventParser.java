package com.mozdzo.ors.search.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.RadioStationUpdated;
import com.mozdzo.ors.search.GenreDocument;
import com.mozdzo.ors.search.RadioStationDocument;
import com.mozdzo.ors.search.RadioStationsRepository;
import com.mozdzo.ors.search.commands.FindGenreByUniqueId;
import com.mozdzo.ors.search.commands.FindRadioStationByUniqueId;
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
