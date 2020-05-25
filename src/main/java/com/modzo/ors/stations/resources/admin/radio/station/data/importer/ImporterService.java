package com.modzo.ors.stations.resources.admin.radio.station.data.importer;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.CreateRadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.FindRadioStationByTitle;
import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindRadioStationStreamByUrl;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;

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
        Optional<RadioStation> existingStation = findRadioStationByTitleHandler.handle(
                new FindRadioStationByTitle(entry.getRadioStationName())
        );
        if (existingStation.isPresent()) {
            logger.warn("Radio station name `{}` already exists. Skipping creation.", entry.getRadioStationName());
            createStreamUrls(existingStation.get().getId(), entry.getStreamUrls());
        } else {
            CreateRadioStation.Result result = createRadioStationHandler.handle(
                    new CreateRadioStation(entry.getRadioStationName())
            );
            createStreamUrls(result.id, entry.getStreamUrls());
        }
    }

    private void createStreamUrls(Long id, String streamUrls) {
        String[] urls = streamUrls.split("\\|");
        Arrays.stream(urls).forEach(url -> createStreamUrl(id, url));
    }

    private void createStreamUrl(Long id, String streamUrl) {
        if (findRadioStationStreamByUrlHandler.handle(new FindRadioStationStreamByUrl(streamUrl)).isPresent()) {
            logger.warn("Stream url `{}` already exists. Skipping creation.", streamUrl);
            return;
        }

        createRadioStationStreamHandler.handle(new CreateRadioStationStream(id, streamUrl));
    }
}
