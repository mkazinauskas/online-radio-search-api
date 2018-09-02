package com.mozdzo.ors

import groovy.transform.CompileStatic
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

@CompileStatic
class HttpEntityBuilder<B> {

    private B body

    private final HttpHeaders headers = new HttpHeaders()

    static HttpEntityBuilder<B> builder() {
        return new HttpEntityBuilder<B>()
    }

    HttpEntityBuilder<B> body(B body) {
        this.body = body
        return this
    }

    HttpEntityBuilder<B> bearer(String token) {
        headers.add('Authorization', "Bearer ${token}")
        return this
    }

    HttpEntity<B> build() {
        return new HttpEntity(body, headers)
    }

    static HttpEntity<B> quick() {
        return builder().build()
    }
}
