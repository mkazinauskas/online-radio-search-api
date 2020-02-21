package com.modzo.ors.stations.resources

import com.github.tomakehurst.wiremock.http.HttpHeader
import com.github.tomakehurst.wiremock.http.HttpHeaders as WiremockHttpHeaders
import com.modzo.ors.setup.TestWireMockServer
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

import static com.github.tomakehurst.wiremock.client.WireMock.*

@Component
@Slf4j
class WireMockTestHelper {

    @Autowired
    TestWireMockServer testWiremockServer

    void okHeaderResponse(String url) {
        String resourcePath = getPath(url)

        testWiremockServer.server().stubFor(
                head(urlEqualTo(resourcePath))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, 'text/html;utf-8'))
        )
    }

    String okGetResponse(String url, Map<String, String> headers, String body) {
        String resourcePath = getPath(url)

        WiremockHttpHeaders wiremockHeaders = new WiremockHttpHeaders(
                headers.collect { k, v -> new HttpHeader(k, v) }
        )

        testWiremockServer.server().stubFor(
                get(urlEqualTo(resourcePath))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeaders(wiremockHeaders)
                                .withBody(body))
        )

        return testWiremockServer.server().url(resourcePath);
    }

    private static String getPath(String inputUrl) {
        try {
            return new URL(inputUrl).getPath()
        } catch (MalformedURLException ignored) {
            log.info("${inputUrl} is malformed, using it as path")
            return inputUrl
        }
    }

}
