package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.GenreCreated;
import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.GenresRepository;
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
