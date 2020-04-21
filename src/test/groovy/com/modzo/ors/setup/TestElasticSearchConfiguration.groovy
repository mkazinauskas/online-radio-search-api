package com.modzo.ors.setup

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Component
@ConfigurationProperties(prefix = 'test.elastic.search')
class TestElasticSearchConfiguration {

    @Min(1L)
    int port

    @Min(1L)
    int apiPort

    @NotBlank
    String clusterName
}
