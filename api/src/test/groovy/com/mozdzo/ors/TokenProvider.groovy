package com.mozdzo.ors

import org.keycloak.test.TestsHelper
import org.springframework.stereotype.Component

@Component
class TokenProvider {

    static String token(TestUsers.TestUser user) {
        return TestsHelper.getToken(user.username, user.password, TestsHelper.testRealm)
    }
}
