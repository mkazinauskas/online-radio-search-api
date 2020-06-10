package com.modzo.ors.statistics.provider;

import com.modzo.ors.search.domain.GenresRepository;
import com.modzo.ors.statistics.StatisticProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class GenreDocumentsStatsProvider implements StatisticProvider {

    private final GenresRepository repository;

    public GenreDocumentsStatsProvider(GenresRepository repository) {
        this.repository = repository;
    }

    @Override
    public Type type() {
        return Type.GENRES_DOCUMENTS;
    }

    @Override
    public Map<Subtype, String> values() {
        return Map.ofEntries(
                Map.entry(Subtype.COUNT, String.valueOf(repository.count()))
        );
    }
}
