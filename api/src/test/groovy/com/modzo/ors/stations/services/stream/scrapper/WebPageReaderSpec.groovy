package com.modzo.ors.stations.services.stream.scrapper

import com.modzo.ors.stations.resources.IntegrationSpec
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import spock.lang.Unroll

import static org.springframework.http.HttpHeaders.CONTENT_TYPE

class WebPageReaderSpec extends IntegrationSpec {

    @Autowired
    WebPageReader testTarget

    @Unroll
    void 'should return page header and body when page is of content type is #contentType'() {
        given:
            Map<String, String> headers = [(CONTENT_TYPE): contentType]
            String body = 'Success'
            String requestUrl = mockUrl(headers, body)
        when:
            WebPageReader.Response result = testTarget.read(requestUrl).get()
        then:
            Map<String, String> resultedHeaders = result.getHeaders()
            resultedHeaders
            resultedHeaders.get(CONTENT_TYPE) == contentType
        and:
            result.getBody().get() == body
        where:
            contentType << [
                    MediaType.TEXT_HTML_VALUE,
                    MediaType.TEXT_XML_VALUE,
                    MediaType.TEXT_PLAIN_VALUE
            ]
    }

    @Unroll
    void 'should return page header without body when page is of content type #contentType'() {
        given:
            Map<String, String> headers = [(CONTENT_TYPE): contentType]
            String body = 'This should not be returned'
            String requestUrl = mockUrl(headers, body)
        when:
            WebPageReader.Response result = testTarget.read(requestUrl).get()
        then:
            Map<String, String> resultedHeaders = result.getHeaders()
            resultedHeaders
            resultedHeaders.get(CONTENT_TYPE) == contentType
        and:
            result.getBody().isEmpty()
        where:
            contentType << [
                    'audio/mpeg',
                    'audio/mp3'
            ]
    }

    private String mockUrl(Map<String, String> headers, String body = null) {
        String path = '/' + RandomStringUtils.randomAlphanumeric(10)
        return wireMockTestHelper.okGetResponse(path, headers, body)
    }
}
