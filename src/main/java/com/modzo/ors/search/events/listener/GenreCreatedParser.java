package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.GenresRepository;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.Genres;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class GenreCreatedParser implements EventParser {

    private final GenresRepository genresRepository;

    private final Genres genres;

    public GenreCreatedParser(GenresRepository genresRepository, Genres genres) {
        this.genresRepository = genresRepository;
        this.genres = genres;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.GENRE;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.CREATED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        Genre savedGenre = genres.findById(domainEvent.getId()).get();

        GenreDocument genreDocument = new GenreDocument(
                savedGenre.getId(),
                savedGenre.getUniqueId(),
                savedGenre.getTitle()
        );
        genresRepository.save(genreDocument);
    }

}
