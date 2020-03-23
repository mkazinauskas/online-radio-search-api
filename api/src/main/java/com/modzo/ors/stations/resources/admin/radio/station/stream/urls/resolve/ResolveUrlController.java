package com.modzo.ors.stations.resources.admin.radio.station.stream.urls.resolve;

import com.modzo.ors.stations.services.stream.url.UrlResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.accepted;

@RestController
class ResolveUrlController {

    private final UrlResolver urlResolver;

    public ResolveUrlController(UrlResolver urlResolver) {
        this.urlResolver = urlResolver;
    }

    @PutMapping("/admin/radio-stations/{id}/streams/{streamId}/urls")
    ResponseEntity<String> resolveUrl(@PathVariable("id") long radioStationId,
                                      @PathVariable("streamId") long streamId,
                                      @RequestBody @Validated ResolveUrlRequest request) {
        urlResolver.resolve(radioStationId, streamId, request.getType());
        return accepted().build();
    }
}
