package com.modzo.ors.stations.resources.radio.station;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class RadioStationsModel extends PagedModel<RadioStationModel> {

    private RadioStationsModel() {
    }

    private RadioStationsModel(Collection<RadioStationModel> content, PageMetadata metadata, Link... links) {
        super(content, metadata, links);
    }

    static RadioStationsModel create(RadioStationsFilter filter,
                                     Page<RadioStation> radioStations,
                                     Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                radioStations.getSize(),
                radioStations.getNumber(),
                radioStations.getTotalElements(),
                radioStations.getTotalPages()
        );
        Collection<RadioStationModel> resources = radioStations.getContent()
                .stream()
                .map(RadioStationModel::create)
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationController.class)
                .getRadioStations(filter, pageable)).withSelfRel();

        return new RadioStationsModel(
                resources,
                pageMetadata,
                link
        );
    }

    static RadioStationsModel createForSongId(long songId, Page<RadioStation> radioStations, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                radioStations.getSize(),
                radioStations.getNumber(),
                radioStations.getTotalElements(),
                radioStations.getTotalPages()
        );
        Collection<RadioStationModel> resources = radioStations.getContent()
                .stream()
                .map(RadioStationModel::create)
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationController.class)
                .getRadioStationBySongId(songId, pageable)).withSelfRel();

        return new RadioStationsModel(
                resources,
                pageMetadata,
                link
        );
    }

    static RadioStationsModel createForGenreId(long genreId, Page<RadioStation> radioStations, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                radioStations.getSize(),
                radioStations.getNumber(),
                radioStations.getTotalElements(),
                radioStations.getTotalPages()
        );
        Collection<RadioStationModel> resources = radioStations.getContent()
                .stream()
                .map(RadioStationModel::create)
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationController.class)
                .getRadioStationBySongId(genreId, pageable)).withSelfRel();

        return new RadioStationsModel(
                resources,
                pageMetadata,
                link
        );
    }
}
