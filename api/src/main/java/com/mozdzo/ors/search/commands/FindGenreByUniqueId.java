package com.mozdzo.ors.search.commands;

import com.mozdzo.ors.search.GenreDocument;
import com.mozdzo.ors.search.GenresRepository;
import com.mozdzo.ors.search.ReadModelException;
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

