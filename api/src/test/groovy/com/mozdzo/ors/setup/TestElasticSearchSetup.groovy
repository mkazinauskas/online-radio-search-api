package com.mozdzo.ors.setup

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy
import org.testcontainers.containers.wait.strategy.WaitStrategy

import static java.time.Duration.ofSeconds

@ActiveProfiles(value = 'test')
@Component
@Slf4j
class TestElasticSearchSetup implements ParallelInitializationBean, DisposableBean {
    private final TestElasticSearchConfiguration elasticSearchConfig
    private final ElasticSearchContainer container

    TestElasticSearchSetup(TestElasticSearchConfiguration elasticSearchConfig) {
        this.elasticSearchConfig = elasticSearchConfig
        this.container = new ElasticSearchContainer(
                elasticSearchConfig.port,
                elasticSearchConfig.apiPort,
                elasticSearchConfig.clusterName
        )
    }

    @Override
    void initialize() throws Exception {
        container.start()
    }

    @Override
    void destroy() throws Exception {
        container.stop()
    }

    static class ElasticSearchContainer {
        private final static int ELASTIC_SEARCH_PORT = 9200
        private final static int ELASTIC_SEARCH_API_PORT = 9300

        private final static WaitStrategy WAITING_STRATEGY = new HttpWaitStrategy()
                .forPath('/')
                .forPort(ELASTIC_SEARCH_PORT)

        private final GenericContainer container

        ElasticSearchContainer(int port, int apiPort, String clusterName) {
            this.container = new FixedHostPortGenericContainer(
                    'elasticsearch:6.4.1'
            )
                    .withFixedExposedPort(port, ELASTIC_SEARCH_PORT)
                    .withFixedExposedPort(apiPort, ELASTIC_SEARCH_API_PORT)
                    .withEnv(['discovery.type'        : 'single-node',
                              'xpack.security.enabled': 'false',
                              'network.host'          : '_site_',
                              'network.publish_host'  : '_local_',
                              'cluster.name'          : clusterName
            ])

                    .withMinimumRunningDuration(ofSeconds(10))
                    .waitingFor(WAITING_STRATEGY)
        }

        void start() {
            container.start()
        }

        void stop() {
            container.stop()
        }
    }
}
