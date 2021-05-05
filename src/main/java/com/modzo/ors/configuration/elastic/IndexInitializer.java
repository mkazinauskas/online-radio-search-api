package com.modzo.ors.configuration.elastic;

import com.modzo.ors.last.searches.domain.SearchedQuery;
import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.RadioStationDocument;
import com.modzo.ors.search.domain.SongDocument;
import org.elasticsearch.common.collect.MapBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
class IndexInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(IndexInitializer.class);

    private final ElasticsearchRestTemplate template;

    public IndexInitializer(ElasticsearchRestTemplate template) {
        this.template = template;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> indexSettings = MapBuilder.<String, String>newMapBuilder()
                .put("number_of_shards", "5")
                .put("number_of_replicas", "1")
                .immutableMap();

        Set<Class> indexes = new HashSet<>();
        indexes.add(GenreDocument.class);
        indexes.add(SearchedQuery.class);
        indexes.add(RadioStationDocument.class);
        indexes.add(SongDocument.class);

        indexes.forEach(index -> createIndex(indexSettings, index));
    }

    private void createIndex(Map<String, String> indexSettings, Class index) {
        try {
            boolean indexExists = template.indexOps(index).exists();
            if (indexExists) {
                return;
            }
            LOG.info("Initializing index for`{}`", index);
            IndexOperations indexOperations = template.indexOps(index);

            org.springframework.data.elasticsearch.core.document.Document settings = indexOperations.createSettings();
            settings.putAll(indexSettings);

            indexOperations.create(settings);
        } catch (Exception ex) {
            LOG.error("Failed to create index " + index, ex);
        }
    }

}