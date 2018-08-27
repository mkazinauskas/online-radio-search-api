package com.mozdzo.ors.helpers

import org.springframework.http.HttpHeaders

class Location {
    static long formHeader(HttpHeaders headers){
        headers.getLocation().path.split('/').last() as long
    }
}
