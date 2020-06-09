package com.modzo.ors.stations.resources.admin.radio.station.data.importer;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.commands.CreateRadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.FindRadioStationByTitle;
import com.modzo.ors.stations.domain.radio.station.commands.UpdateRadioStation;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindRadioStationStreamByUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.UpdateRadioStationStream;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class ImporterService {

    private static final Logger logger = LoggerFactory.getLogger(ImporterService.class);

    private final FindRadioStationByTitle.Handler findRadioStationByTitleHandler;

    private final CreateRadioStation.Handler createRadioStationHandler;

    private final CreateRadioStationStream.Handler createRadioStationStreamHandler;

    private final FindRadioStationStreamByUrl.Handler findRadioStationStreamByUrlHandler;

    private final UpdateRadioStationStream.Handler updateRadioStationStreamHandler;

    private final UpdateRadioStation.Handler updateRadioStationHandler;

    private final RadioStations radioStations;

    ImporterService(FindRadioStationByTitle.Handler findRadioStationByTitleHandler,
                    CreateRadioStation.Handler createRadioStationHandler,
                    CreateRadioStationStream.Handler createRadioStationStreamHandler,
                    FindRadioStationStreamByUrl.Handler findRadioStationStreamByUrlHandler,
                    UpdateRadioStationStream.Handler updateRadioStationStreamHandler,
                    UpdateRadioStation.Handler updateRadioStationHandler,
                    RadioStations radioStations) {
        this.findRadioStationByTitleHandler = findRadioStationByTitleHandler;
        this.createRadioStationHandler = createRadioStationHandler;
        this.createRadioStationStreamHandler = createRadioStationStreamHandler;
        this.findRadioStationStreamByUrlHandler = findRadioStationStreamByUrlHandler;
        this.updateRadioStationStreamHandler = updateRadioStationStreamHandler;
        this.updateRadioStationHandler = updateRadioStationHandler;
        this.radioStations = radioStations;
    }

    void run(MultipartFile file) {
        try {
            CsvReader.read(file)
                    .forEach(this::doImport);
        } catch (Exception exception) {
            logger.error("Failed to import radio stations", exception);
            throw new DomainException(
                    "FAILED_TO_IMPORT_RADIO_STATIONS",
                    "file",
                    exception.getMessage()
            );
        }
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

        List<Boolean> isWorkingFlag = toWorkingFlags(entry.getStreamIsWorking());
        if (isWorkingFlag.size() < streamUrls.size()) {
            logger.warn(
                    "Radio station name `{}` does not have the same amount of enabled streams flags. " +
                            "Skipping creation.",
                    radioStationName
            );
            return;
        }

        Optional<RadioStation> existingStationByUniqueId = radioStations.findByUniqueId(entry.getRadioStationUniqueId());
        if (existingStationByUniqueId.isPresent()) {
            logger.warn("Radio station uuid `{}` already exists. Skipping creation.", entry.getRadioStationUniqueId());
            createStreamUrls(existingStationByUniqueId.get().getId(), streamUrls, isWorkingFlag);
            return;
        }

        Optional<RadioStation> existingStationByTitle = findRadioStationByTitleHandler.handle(
                new FindRadioStationByTitle(radioStationName)
        );

        if (existingStationByTitle.isPresent()) {
            logger.warn("Radio station name `{}` already exists. Skipping creation.", radioStationName);
            createStreamUrls(existingStationByTitle.get().getId(), streamUrls, isWorkingFlag);
        } else {
            CreateRadioStation.Result result = createRadioStationHandler.handle(
                    new CreateRadioStation(entry.getRadioStationUniqueId(), radioStationName)
            );
            createStreamUrls(result.id, streamUrls, isWorkingFlag);

            RadioStation currentRadioStation = radioStations.findById(result.id).get();

            updateRadioStationHandler.handle(new UpdateRadioStation(result.id, new UpdateRadioStation.DataBuilder()
                    .fromCurrent(currentRadioStation)
                    .setEnabled(entry.isRadioStationEnabled())
                    .build())
            );
        }
    }

    private List<String> toUrls(String streamUrls) {
        if (isBlank(streamUrls)) {
            return List.of();
        }
        return Arrays.stream(streamUrls.split("\\|"))
                .filter(StringUtils::isNotBlank)
                .filter(url -> url.length() <= 100)
                .collect(toList());
    }

    private List<Boolean> toWorkingFlags(String workingChecks) {
        if (isBlank(workingChecks)) {
            return List.of();
        }
        return Arrays.stream(workingChecks.split("\\|"))
                .filter(StringUtils::isNotBlank)
                .map(Boolean::valueOf)
                .collect(toList());
    }

    private void createStreamUrls(Long id, List<String> streamUrls, List<Boolean> isWorkingFlags) {
        IntStream.range(0, streamUrls.size())
                .forEach(index -> createStreamUrl(id, streamUrls.get(index), isWorkingFlags.get(index)));
    }

    private void createStreamUrl(Long radioStationId, String streamUrl, boolean isWorking) {
        if (findRadioStationStreamByUrlHandler.handle(new FindRadioStationStreamByUrl(streamUrl)).isPresent()) {
            logger.warn("Stream url `{}` already exists. Skipping creation.", streamUrl);
            return;
        }

        CreateRadioStationStream.Result result = createRadioStationStreamHandler.handle(
                new CreateRadioStationStream(radioStationId, streamUrl)
        );

        RadioStationStream savedStream = findRadioStationStreamByUrlHandler
                .handle(new FindRadioStationStreamByUrl(streamUrl)).get();

        updateRadioStationStreamHandler.handle(
                new UpdateRadioStationStream(
                        radioStationId,
                        result.id,
                        new UpdateRadioStationStream.DataBuilder()
                                .setUrl(savedStream.getUrl())
                                .setBitRate(savedStream.getBitRate())
                                .setType(savedStream.getType().orElse(null))
                                .setWorking(isWorking)
                                .build()
                )
        );
    }
}
