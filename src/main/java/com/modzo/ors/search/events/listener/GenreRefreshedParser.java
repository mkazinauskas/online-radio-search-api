package com.modzo.ors.search.events.listener;

import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.GenresRepository;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.Genres;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GenreRefreshedParser implements EventParser {

    private final GenresRepository genresRepository;

    private final Genres genres;

    public GenreRefreshedParser(GenresRepository genresRepository, Genres genres) {
        this.genresRepository = genresRepository;
        this.genres = genres;
    }

    @Override
    public StationsDomainEvent.Type type() {
        return StationsDomainEvent.Type.GENRE;
    }

    @Override
    public StationsDomainEvent.Action action() {
        return StationsDomainEvent.Action.REFRESHED;
    }

    @Override
    public void process(StationsDomainEvent domainEvent) {
        Genre savedGenre = genres.findById(domainEvent.getId()).get();

        Optional<GenreDocument> document = genresRepository.findById(domainEvent.getId());
        if (document.isPresent()) {
            GenreDocument existingDocument = document.get();
            existingDocument.setTitle(savedGenre.getTitle());
            genresRepository.save(existingDocument);
        } else {
            GenreDocument genreDocument = new GenreDocument(
                    savedGenre.getId(),
                    savedGenre.getUniqueId(),
                    savedGenre.getTitle()
            );
            genresRepository.save(genreDocument);
        }
    }

}
