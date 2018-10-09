package com.mozdzo.ors.setup

import groovy.util.logging.Slf4j
import org.keycloak.test.TestsHelper
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
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
class TestElasticSearchSetup implements InitializingBean, DisposableBean {
    private final TestElasticSearchConfiguration elasticSearchConfig
    private final ElasticSearchContainer container

    TestElasticSearchSetup(TestElasticSearchConfiguration elasticSearchConfig) {
        this.elasticSearchConfig = elasticSearchConfig
        this.container = new ElasticSearchContainer(
                elasticSearchConfig.username, elasticSearchConfig.password, elasticSearchConfig.port
        )
    }

    @Override
    void afterPropertiesSet() throws Exception {
        container.start()
    }

    @Override
    void destroy() throws Exception {
        container.stop()
    }

    static class ElasticSearchContainer {
        private final static int ELASTIC_SEARCH_PORT = 9200

        private final static WaitStrategy WAITING_STRATEGY = new HttpWaitStrategy()
                .forPath('/')
                .forPort(ELASTIC_SEARCH_PORT)

        private final GenericContainer container

        ElasticSearchContainer(String username, String password, int port) {
            this.container = new FixedHostPortGenericContainer(
                    'elasticsearch:6.4.1'
            )
                    .withFixedExposedPort(port, ELASTIC_SEARCH_PORT)
                    .withEnv(['discovery.type': 'single-node'])
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
