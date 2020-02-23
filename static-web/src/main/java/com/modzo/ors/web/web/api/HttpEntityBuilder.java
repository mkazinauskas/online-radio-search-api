package com.modzo.ors.web.web.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class HttpEntityBuilder<B> {

    private B body;

    private final HttpHeaders headers = new HttpHeaders();

    public static <B> HttpEntityBuilder<B> builder() {
        return new HttpEntityBuilder<>();
    }

    public HttpEntityBuilder<B> body(B body) {
        this.body = body;
        return this;
    }

    public HttpEntity<B> build() {
        return new HttpEntity<>(body, headers);
    }
}
