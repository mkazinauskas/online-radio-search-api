package com.modzo.ors.stations.resources.admin.radio.station.data.exporter;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStreams;
import com.modzo.ors.stations.resources.admin.radio.station.data.BackupData;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class ExporterService {

    private final GetRadioStations.Handler getRadioStationsHandler;

    private final GetRadioStationStreams.Handler getRadioStationStreamsHandler;

    public ExporterService(GetRadioStations.Handler getRadioStationsHandler,
                           GetRadioStationStreams.Handler getRadioStationStreamsHandler) {
        this.getRadioStationsHandler = getRadioStationsHandler;
        this.getRadioStationStreamsHandler = getRadioStationStreamsHandler;
    }

    Resource export(Pageable pageable) {
        Page<BackupData> radioStations = getRadioStationsHandler.handle(new GetRadioStations(pageable))
                .map(this::map);

        byte[] result = JsonCreator.write(radioStations.getContent());

        return new ByteArrayResource(result);
    }

    private BackupData map(RadioStation station) {
        List<RadioStationStream> radioStationStreams = getRadioStationStreamsHandler
                .handle(new GetRadioStationStreams(station.getId(), Pageable.unpaged()))
                .getContent();

        List<BackupData.Stream> streams = radioStationStreams.stream()
                .map(stream -> new BackupData.Stream(stream.getUrl(), stream.isWorking()))
                .collect(Collectors.toList());

        return new BackupData(station.getUniqueId(), station.getTitle(), station.isEnabled(), streams);
    }

}
