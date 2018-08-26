package com.mozdzo.ors.resources

import com.mozdzo.ors.HttpEntityBuilder
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.RequestBuilder

import static com.mozdzo.ors.TestUsers.ADMIN
import static com.mozdzo.ors.TestUsers.USER
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.*

class KeycloakApplicationResourceSpec extends IntegrationSpec {

    def 'should access admin resource with admin token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello Admin'

            RequestBuilder
    }

    def 'should access admin resource with admin token post'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/hello',
                    POST,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello Admin'

            RequestBuilder
    }

    def 'should forbid access to admin resource with user token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/admin/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(USER))
                            .build(),
                    String
            )
        then:
            response.statusCode == FORBIDDEN
            response.body.contains('Forbidden')
    }

    def 'should forbid access to admin resource with no token'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/admin/hello', String)
        then:
            response.statusCode == UNAUTHORIZED
    }

    def 'should access user resource with user token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/user/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(USER))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello User'

            RequestBuilder
    }

    def 'should forbid access to user resource with admin token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/user/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == FORBIDDEN
            response.body.contains('Forbidden')
    }

    def 'should forbid access to user resource with no token'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/user/hello', String)
        then:
            response.statusCode == UNAUTHORIZED
    }

    def 'should allow access public resource with admin token'() {
        when:
            ResponseEntity<String> response = restTemplate.exchange(
                    '/hello',
                    GET,
                    HttpEntityBuilder.builder()
                            .bearer(tokenProvider.token(ADMIN))
                            .build(),
                    String
            )
        then:
            response.statusCode == OK
            response.body == 'Hello Anonymous'
    }

    def 'should allow to access public resource'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/hello', String)
        then:
            response.statusCode == OK
            response.body == 'Hello Anonymous'
    }
}
