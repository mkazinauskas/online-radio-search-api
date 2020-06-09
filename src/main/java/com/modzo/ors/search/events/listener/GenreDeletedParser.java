package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.GenresRepository;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class GenreDeletedParser implements EventParser {

    private final GenresRepository genresRepository;

    public GenreDeletedParser(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.GENRE;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.DELETED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        genresRepository.deleteById(domainEvent.getId());
    }

}
