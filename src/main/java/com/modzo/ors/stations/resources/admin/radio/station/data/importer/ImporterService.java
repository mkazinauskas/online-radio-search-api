package com.modzo.ors.stations.resources.admin.radio.station.data.importer;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.CreateRadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.FindRadioStationByTitle;
import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindRadioStationStreamByUrl;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData;
import liquibase.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
class ImporterService {

    private static final Logger logger = LoggerFactory.getLogger(ImporterService.class);

    private final FindRadioStationByTitle.Handler findRadioStationByTitleHandler;

    private final CreateRadioStation.Handler createRadioStationHandler;

    private final CreateRadioStationStream.Handler createRadioStationStreamHandler;

    private final FindRadioStationStreamByUrl.Handler findRadioStationStreamByUrlHandler;

    ImporterService(FindRadioStationByTitle.Handler findRadioStationByTitleHandler,
                    CreateRadioStation.Handler createRadioStationHandler,
                    CreateRadioStationStream.Handler createRadioStationStreamHandler,
                    FindRadioStationStreamByUrl.Handler findRadioStationStreamByUrlHandler) {
        this.findRadioStationByTitleHandler = findRadioStationByTitleHandler;
        this.createRadioStationHandler = createRadioStationHandler;
        this.createRadioStationStreamHandler = createRadioStationStreamHandler;
        this.findRadioStationStreamByUrlHandler = findRadioStationStreamByUrlHandler;
    }

    void run(MultipartFile file) {
        CsvReader.read(file)
                .forEach(this::doImport);
    }

    private void doImport(CsvData entry) {
        String radioStationName = StringUtils.substring(entry.getRadioStationName(), 0, 99);

        List<String> streamUrls = toUrls(entry.getStreamUrls());
        if (streamUrls.isEmpty()) {
            logger.warn(
                    "Radio station name `{}` does not have importable streams. Skipping creation.",
                    radioStationName
            );
            return;
        }

        Optional<RadioStation> existingStation = findRadioStationByTitleHandler.handle(
                new FindRadioStationByTitle(radioStationName)
        );
        if (existingStation.isPresent()) {
            logger.warn("Radio station name `{}` already exists. Skipping creation.", radioStationName);
            createStreamUrls(existingStation.get().getId(), streamUrls);
        } else {
            CreateRadioStation.Result result = createRadioStationHandler.handle(
                    new CreateRadioStation(radioStationName)
            );
            createStreamUrls(result.id, streamUrls);
        }
    }

    private List<String> toUrls(String streamUrls) {
        return Arrays.stream(streamUrls.split("\\|"))
                .filter(url -> url.length() <= 100)
                .collect(toList());
    }

    private void createStreamUrls(Long id, List<String> streamUrls) {
        streamUrls.forEach(url -> createStreamUrl(id, url));
    }

    private void createStreamUrl(Long id, String streamUrl) {
        if (findRadioStationStreamByUrlHandler.handle(new FindRadioStationStreamByUrl(streamUrl)).isPresent()) {
            logger.warn("Stream url `{}` already exists. Skipping creation.", streamUrl);
            return;
        }

        createRadioStationStreamHandler.handle(new CreateRadioStationStream(id, streamUrl));
    }
}
