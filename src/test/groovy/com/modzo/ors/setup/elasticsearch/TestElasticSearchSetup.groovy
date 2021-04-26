package com.modzo.ors.setup.elasticsearch

import groovy.util.logging.Slf4j
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.stereotype.Component
import org.testcontainers.elasticsearch.ElasticsearchContainer

@Component
@Slf4j
class TestElasticSearchSetup {
    @Bean
    @Primary
    RestHighLevelClient testElasticSearchClient() {
        ElasticsearchContainer container = new ElasticsearchContainer()
        container.start()

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(container.getHttpHostAddress())
                .build()

        return RestClients.create(clientConfiguration).rest()
    }

}
