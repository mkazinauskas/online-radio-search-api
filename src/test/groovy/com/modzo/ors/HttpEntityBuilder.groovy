package com.modzo.ors

import groovy.transform.CompileStatic
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@CompileStatic
class HttpEntityBuilder<B> {

    private B body

    private final HttpHeaders headers = new HttpHeaders()

    static HttpEntityBuilder builder() {
        return new HttpEntityBuilder()
    }

    HttpEntityBuilder<B> body(B body) {
        this.body = body
        return this
    }

    HttpEntityBuilder<B> body(String key, B body) {
        this.body = [key: body] as B
        return this
    }

    HttpEntityBuilder<B> bearer(String token) {
        headers.add('Authorization', "Bearer ${token}".toString())
        return this
    }

    HttpEntityBuilder<B> header(String headerName, String headerValue) {
        headers.add(headerName, headerValue)
        return this
    }

    HttpEntity<B> build() {
        return new HttpEntity(body, headers)
    }

    static HttpEntity quick() {
        return builder().build()
    }
}
