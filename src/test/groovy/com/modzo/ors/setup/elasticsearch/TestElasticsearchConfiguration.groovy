package com.modzo.ors.setup.elasticsearch

import groovy.util.logging.Slf4j
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Primary
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
import org.springframework.stereotype.Component
import org.testcontainers.elasticsearch.ElasticsearchContainer

@Component
@Slf4j
@Primary
class TestElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

    @Override
    RestHighLevelClient elasticsearchClient() {
        ElasticsearchContainer container = new ElasticsearchContainer()
        container.start()

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(container.getHttpHostAddress())
                .build()

        return RestClients.create(clientConfiguration).rest()
    }

}
