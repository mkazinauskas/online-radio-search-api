package com.modzo.ors.stations.resources

import com.github.tomakehurst.wiremock.http.HttpHeader as WiremockHttpHeader
import com.github.tomakehurst.wiremock.http.HttpHeaders as WiremockHttpHeaders
import com.modzo.ors.setup.TestWireMockServer
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

@Component
@Slf4j
@CompileStatic
class WireMockTestHelper {

    @Autowired
    TestWireMockServer testWiremockServer

    String okGetResponse(String url, Map<String, String> headers, String body) {
        String resourcePath = getPath(url)

        WiremockHttpHeaders wiremockHeaders = new WiremockHttpHeaders()

        if (!CollectionUtils.isEmpty(headers)) {
            List<WiremockHttpHeader> converted = headers.collect { k, v -> new WiremockHttpHeader(k, v) }
            wiremockHeaders = new WiremockHttpHeaders(converted)
        }

        testWiremockServer.server().stubFor(
                get(urlEqualTo(resourcePath))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeaders(wiremockHeaders)
                                .withBody(body))
        )

        return testWiremockServer.server().url(resourcePath)
    }

    String notFoundResponse(String url) {
        String resourcePath = getPath(url)

        testWiremockServer.server().stubFor(
                get(urlEqualTo(resourcePath))
                        .willReturn(aResponse().withStatus(404))
        )

        return testWiremockServer.server().url(resourcePath)
    }

    private static String getPath(String inputUrl) {
        try {
            return new URL(inputUrl).path
        } catch (MalformedURLException ignored) {
            log.info("${inputUrl} is malformed, using it as path")
            return inputUrl
        }
    }

}
