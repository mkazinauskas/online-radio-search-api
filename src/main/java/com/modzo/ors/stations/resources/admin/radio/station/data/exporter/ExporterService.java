package com.modzo.ors.stations.resources.admin.radio.station.data.exporter;

import org.springframework.stereotype.Component;

@Component
class ExporterService {

//    private static final Logger logger = LoggerFactory.getLogger(ExporterService.class);
//
//    private final FindRadioStationByTitle.Handler findRadioStationByTitleHandler;
//
//    private final CreateRadioStation.Handler createRadioStationHandler;
//
//    private final CreateRadioStationStream.Handler createRadioStationStreamHandler;
//
//    private final FindRadioStationStreamByUrl.Handler findRadioStationStreamByUrlHandler;
//
//    ExporterService(FileReader fileReader,
//                    FindRadioStationByTitle.Handler findRadioStationByTitleHandler,
//                    CreateRadioStation.Handler createRadioStationHandler,
//                    CreateRadioStationStream.Handler createRadioStationStreamHandler,
//                    FindRadioStationStreamByUrl.Handler findRadioStationStreamByUrlHandler) {
//        this.fileReader = fileReader;
//        this.findRadioStationByTitleHandler = findRadioStationByTitleHandler;
//        this.createRadioStationHandler = createRadioStationHandler;
//        this.createRadioStationStreamHandler = createRadioStationStreamHandler;
//        this.findRadioStationStreamByUrlHandler = findRadioStationStreamByUrlHandler;
//    }
//
//    Resource export(Pageable pageable) {
//        fileReader.read(file)
//                .forEach(this::doImport);
//
//        Path path = Paths.get(file.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//        return resource;
//    }
//
//    private void doImport(ImportEntry entry) {
//        Optional<RadioStation> existingStation = findRadioStationByTitleHandler.handle(
//                new FindRadioStationByTitle(entry.getRadioStationName())
//        );
//        if (existingStation.isPresent()) {
//            logger.warn("Radio station name `{}` already exists. Skipping creation.", entry.getRadioStationName());
//            createStream(existingStation.get().getId(), entry.getStreamUrl());
//        } else {
//            CreateRadioStation.Result result = createRadioStationHandler.handle(
//                    new CreateRadioStation(entry.getRadioStationName())
//            );
//            createStream(result.id, entry.getStreamUrl());
//        }
//    }
//
//    private void createStream(Long id, String streamUrl) {
//        if (findRadioStationStreamByUrlHandler.handle(new FindRadioStationStreamByUrl(streamUrl)).isPresent()) {
//            logger.warn("Stream url `{}` already exists. Skipping creation.", streamUrl);
//            return;
//        }
//
//        createRadioStationStreamHandler.handle(new CreateRadioStationStream(id, streamUrl));
//    }

}
