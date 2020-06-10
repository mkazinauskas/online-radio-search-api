package com.modzo.ors.configuration.elastic;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
class IndexInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(IndexInitializer.class);

    private final Client client;

    public IndexInitializer(Client client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Initializing indexes");
        Settings indexSettings = Settings.builder()
                .put("number_of_shards", 5)
                .put("number_of_replicas", 1)
                .build();
        Set<String> indexes = new HashSet<>();
        indexes.add("online_radio_search_genres");
        indexes.add("online_radio_search_searched_queries");
        indexes.add("online_radio_search_radio_stations");
        indexes.add("online_radio_search_songs");

        IndicesAdminClient indices = client.admin().indices();
        indexes.forEach(index -> createIndex(indexSettings, indices, index));
    }

    private void createIndex(Settings indexSettings, IndicesAdminClient indices, String index) {
        try {
            GetAliasesResponse aliases = indices.getAliases(new GetAliasesRequest()).actionGet();
            if (aliases.getAliases().containsKey(index)) {
                return;
            }
            indices.create(new CreateIndexRequest(index, indexSettings)).actionGet();
        } catch (Exception ex) {
            LOG.error("Failed to create index " + index, ex);
        }
    }

}