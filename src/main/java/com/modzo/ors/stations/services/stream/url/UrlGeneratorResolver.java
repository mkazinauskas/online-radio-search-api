package com.modzo.ors.stations.services.stream.url;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Component
class UrlGeneratorResolver {

    private final Map<StreamUrl.Type, StreamUrlGenerator> generators;

    UrlGeneratorResolver(List<StreamUrlGenerator> generators) {
        this.generators = generators.stream()
                .collect(toMap(StreamUrlGenerator::forType, it -> it));
    }

    StreamUrlGenerator ofType(StreamUrl.Type type) {
        return Optional.ofNullable(generators.get(type))
                .orElseThrow(() -> new UrlResolverNotFound(type));
    }

    public static class UrlResolverNotFound extends DomainException {

        public UrlResolverNotFound(StreamUrl.Type type) {
            super(
                    "URL_TYPE_RESOLVER_NOT_FOUND",
                    "type",
                    String.format("Url resolver by type = `%s` was not found", type.toString())
            );
        }
    }
}
