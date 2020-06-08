package com.modzo.ors.setup

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import javax.validation.constraints.Min

@Component
@ConfigurationProperties(prefix = 'test.wiremock')
class WiremockConfiguration {

    @Min(1L)
    int httpPort

}
