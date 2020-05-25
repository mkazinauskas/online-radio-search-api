package com.modzo.ors.stations.resources.admin.radio.station.data.exporter;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStreams;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class ExporterService {

    private static final Logger logger = LoggerFactory.getLogger(ExporterService.class);

    private final GetRadioStations.Handler getRadioStationsHandler;

    private final GetRadioStationStreams.Handler getRadioStationStreamsHandler;

    public ExporterService(GetRadioStations.Handler getRadioStationsHandler,
                           GetRadioStationStreams.Handler getRadioStationStreamsHandler) {
        this.getRadioStationsHandler = getRadioStationsHandler;
        this.getRadioStationStreamsHandler = getRadioStationStreamsHandler;
    }

    Resource export(Pageable pageable) {
        Page<CsvData> radioStations = getRadioStationsHandler.handle(new GetRadioStations(pageable))
                .map(this::map);

        byte[] result = CsvCreator.write(radioStations.getContent());

        return new ByteArrayResource(result);
    }

    private CsvData map(RadioStation station) {
        String streams = getRadioStationStreamsHandler
                .handle(new GetRadioStationStreams(station.getId(), Pageable.unpaged()))
                .getContent().stream()
                .map(RadioStationStream::getUrl)
                .collect(Collectors.joining("|"));

        return new CsvData(station.getTitle(), streams);
    }

}
