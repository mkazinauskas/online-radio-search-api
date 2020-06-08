package com.modzo.ors.search.domain.events.reader.parser;

import com.modzo.ors.events.domain.DomainEvent;
import com.modzo.ors.events.domain.Event;
import com.modzo.ors.events.domain.GenreDeleted;
import com.modzo.ors.search.domain.GenresRepository;
import org.springframework.stereotype.Component;

@Component
class GenreDeletedEventParser implements EventParser {

    private final GenresRepository genresRepository;

    public GenreDeletedEventParser(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Override
    public Class<? extends DomainEvent.Data> eventClass() {
        return Event.Type.GENRE_DELETED.getEventClass();
    }

    @Override
    public void parse(Event event) {
        GenreDeleted.Data data = GenreDeleted.Data.deserialize(event.getBody());

        genresRepository.deleteByUniqueId(data.getUniqueId());
    }
}
