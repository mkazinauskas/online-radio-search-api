package com.modzo.ors.resources

import com.modzo.ors.HttpEntityBuilder
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.RequestBuilder

import static com.modzo.ors.TestUsers.ADMIN
import static com.modzo.ors.TestUsers.USER
import static com.modzo.ors.TokenProvider.token
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.*

class KeycloakApplicationControllerSpec extends IntegrationSpec {

    void 'should access admin resource with admin token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello Admin'

            RequestBuilder
    }

    void 'should access admin resource with admin token post'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/hello',
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello Admin'

            RequestBuilder
    }

    void 'should forbid access to admin resource with user token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(USER))
                            .build(),
                    String
            )
        then:
            response.statusCode == FORBIDDEN
            response.body.contains('Forbidden')
    }

    void 'should forbid access to admin resource with no token'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/admin/hello', String)
        then:
            response.statusCode == UNAUTHORIZED
    }

    void 'should access user resource with user token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/user/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(USER))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello User'

            RequestBuilder
    }

    void 'should forbid access to user resource with admin token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/user/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == FORBIDDEN
            response.body.contains('Forbidden')
    }

    void 'should forbid access to user resource with no token'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/user/hello', String)
        then:
            response.statusCode == UNAUTHORIZED
    }

    void 'should allow access public resource with admin token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello Anonymous'
    }

    void 'should allow to access public resource'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/hello', String)
        then:
            response.statusCode == OK
            response.body == 'Hello Anonymous'
    }
}
