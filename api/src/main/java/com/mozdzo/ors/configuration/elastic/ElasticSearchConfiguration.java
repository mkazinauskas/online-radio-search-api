package com.mozdzo.ors.configuration.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.modzo.ors.search")
class ElasticSearchConfiguration {

    @Value("${elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${elasticsearch.port}")
    private int elasticSearchPort;

    @Value("${elasticsearch.clustername}")
    private String elastiSearchClusterName;

    @Bean
    Client client() throws Exception {
        Settings elasticsearchSettings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", elastiSearchClusterName).build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(
                new InetSocketTransportAddress(
                        InetAddress.getByName(elasticSearchHost), elasticSearchPort));
        return client;
    }

    @Bean
    ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
