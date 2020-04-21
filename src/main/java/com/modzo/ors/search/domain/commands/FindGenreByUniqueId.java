package com.modzo.ors.search.domain.commands;

import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.GenresRepository;
import com.modzo.ors.search.domain.ReadModelException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

public class FindGenreByUniqueId {
    private final String uniqueId;

    public FindGenreByUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Component
    public static class Handler {
        private final GenresRepository genresRepository;

        public Handler(GenresRepository genresRepository) {
            this.genresRepository = genresRepository;
        }

        public GenreDocument handle(FindGenreByUniqueId command) {
            return genresRepository.findByUniqueId(command.uniqueId)
                    .orElseThrow(() -> new ReadModelException(
                            "GENRE_BY_UNIQUE_ID_NOT_FOUND",
                            format("Genre by unique id `%s` was not found",
                                    command.uniqueId)
                    ));
        }
    }
}

