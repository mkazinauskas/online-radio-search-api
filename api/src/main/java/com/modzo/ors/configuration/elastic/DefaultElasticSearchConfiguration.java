package com.modzo.ors.configuration.elastic;

import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories
class DefaultElasticSearchConfiguration {

    @Value("${elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${elasticsearch.port}")
    private int elasticSearchPort;

    @Value("${elasticsearch.clustername}")
    private String elasticSearchClusterName;

    @Bean
    Client client() {
        Settings elasticsearchSettings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", elasticSearchClusterName)
                .build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(resolveTransportAddress());
        return client;
    }

    private TransportAddress resolveTransportAddress() {
        InetAddress inetAddress = Sneaky.sneak(() -> InetAddress.getByName(elasticSearchHost));
        return new TransportAddress(inetAddress, elasticSearchPort);
    }

    @Bean
    ElasticsearchOperations customElasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
