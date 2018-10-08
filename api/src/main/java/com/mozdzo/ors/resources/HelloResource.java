package com.mozdzo.ors.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloResource {

    @GetMapping("/admin/hello")
    String helloAdmin() {
        return "Hello Admin";
    }


    @PostMapping("/admin/hello")
    String helloAdminPost() {
        return "Hello Admin";
    }

    @GetMapping("/user/hello")
    String helloUser() {
        return "Hello User";
    }

    @GetMapping("/hello")
    String helloAnonymous() {
        return "Hello Anonymous";
    }
}
