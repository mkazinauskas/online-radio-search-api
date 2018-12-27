package com.mozdzo.ors.search.domain.reader.parser;

import com.mozdzo.ors.domain.events.DomainEvent;
import com.mozdzo.ors.domain.events.Event;
import com.mozdzo.ors.domain.events.GenreCreated;
import com.mozdzo.ors.search.domain.GenreDocument;
import com.mozdzo.ors.search.domain.GenresRepository;
import org.springframework.stereotype.Component;

@Component
class GenreCreatedEventParser implements EventParser {

    private final GenresRepository genresRepository;

    public GenreCreatedEventParser(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.GENRE_CREATED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        GenreCreated.Data data = GenreCreated.Data.deserialize(event.getBody());

        GenreDocument genreDocument = new GenreDocument(data.getUniqueId(), data.getTitle());

        genresRepository.save(genreDocument);
    }
}
