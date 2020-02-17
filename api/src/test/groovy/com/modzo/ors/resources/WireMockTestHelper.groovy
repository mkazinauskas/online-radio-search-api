package com.modzo.ors.resources

import com.modzo.ors.setup.TestWireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

import static com.github.tomakehurst.wiremock.client.WireMock.*

@Component
class WireMockTestHelper {

    @Autowired
    TestWireMockServer testWiremockServer

    void okHeaderResponse(String url) {
        String resourcePath = getPath(url)
        checkCorrectMockedAddress(testWiremockServer.server().url(resourcePath), url)

        testWiremockServer.server().stubFor(
                head(urlEqualTo(resourcePath))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE, 'text/html;utf-8'))
        )
    }

    void okGetResponse(String url, String body) {
        String resourcePath = getPath(url)
        checkCorrectMockedAddress(testWiremockServer.server().url(resourcePath), url)

        testWiremockServer.server().stubFor(
                get(urlEqualTo(resourcePath))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withBody(body))
        )
    }

    private static String getPath(String inputUrl) {
        URL url = new URL(inputUrl)
        return url.getPath()
    }

    private static void checkCorrectMockedAddress(String mockServerPath, String expectedUrl) {
        assert mockServerPath == expectedUrl
    }
}
