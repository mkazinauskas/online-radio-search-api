package com.modzo.ors.setup

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Component

@Component
class TestWireMockServer implements DisposableBean {

    private final WireMockServer wireMockServer

    TestWireMockServer(WiremockConfiguration wiremockConfiguration) {
        this.wireMockServer = new WireMockServer(wiremockConfiguration.port)
    }

    WireMockServer server() {
        if (!wireMockServer.isRunning()) {
            wireMockServer.start()
        }
        return wireMockServer
    }

    @Override
    void destroy() throws Exception {
        if (wireMockServer.isRunning()) {
            wireMockServer.stop()
        }
    }
}
