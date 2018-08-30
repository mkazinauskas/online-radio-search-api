package com.mozdzo.ors.resources;

import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HateoasHelper {
    public static List<Link> parseLinks(Map<String, Link> links) {
        return links.entrySet().stream()
                .map(map -> new Link(map.getValue().getHref(), map.getKey()))
                .collect(Collectors.toList());
    }
}
