package com.modzo.ors.web.web.api.radio.stations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Map;

import static com.modzo.ors.web.web.api.radio.stations.HateoasHelper.parseLinks;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RadioStationsResource extends PagedModel<RadioStationResource> {

    @JsonCreator
    public RadioStationsResource(
            @JsonProperty("_embedded") Map<String, Collection<RadioStationResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(content.get("radioStationResourceList"), metadata, parseLinks(links));
    }

}
