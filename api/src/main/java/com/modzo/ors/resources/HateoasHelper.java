package com.modzo.ors.resources;

import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HateoasHelper {
    public static List<Link> parseLinks(Map<String, Link> links) {
        if (Objects.isNull(links)) {
            return new ArrayList<>();
        }
        return links.entrySet().stream()
                .map(map -> new Link(map.getValue().getHref(), map.getKey()))
                .collect(Collectors.toList());
    }
}
