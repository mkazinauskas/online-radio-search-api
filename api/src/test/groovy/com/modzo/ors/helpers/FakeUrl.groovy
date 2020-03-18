package com.modzo.ors.helpers

import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

class FakeUrl {

    static create() {
        return "http://www.${RandomStringUtils.randomAlphanumeric(10)}.com"
    }
}
