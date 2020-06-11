package com.modzo.ors.stations.services.stream.url;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.CreateRadioStationStreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStream;
import com.modzo.ors.stations.services.stream.reader.WebPageReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrlResolver {

    private final UrlGeneratorResolver urlGeneratorResolver;

    private final GetRadioStationStream.Handler getRadioStationStreamHandler;

    private final WebPageReader webPageReader;

    private final CreateRadioStationStreamUrl.Handler createRadioStationStreamUrl;

    public UrlResolver(UrlGeneratorResolver urlGeneratorResolver,
                       GetRadioStationStream.Handler getRadioStationStreamHandler,
                       WebPageReader webPageReader,
                       CreateRadioStationStreamUrl.Handler createRadioStationStreamUrl) {
        this.urlGeneratorResolver = urlGeneratorResolver;
        this.getRadioStationStreamHandler = getRadioStationStreamHandler;
        this.webPageReader = webPageReader;
        this.createRadioStationStreamUrl = createRadioStationStreamUrl;
    }

    public void resolve(long radioStationId, long streamId, StreamUrl.Type type) {
        RadioStationStream stream = getRadioStationStreamHandler.handle(
                new GetRadioStationStream(radioStationId, streamId)
        );

        StreamUrlGenerator generator = urlGeneratorResolver.ofType(type);
        List<String> urls = generator.generateUrls(stream.getUrl());

        Optional<WebPageReader.Response> workingUrlResponse = urls
                .stream()
                .map(this.webPageReader::read)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(response -> response.getBody().isPresent())
                .findFirst();

        workingUrlResponse.ifPresent(url -> this.saveUrl(stream, type, url));
    }

    private void saveUrl(RadioStationStream stream, StreamUrl.Type type, WebPageReader.Response response) {
        String url = response.getUrl();
        boolean urlExists = stream.findUrl(type)
                .filter(streamUrl -> StringUtils.equalsIgnoreCase(stream.getUrl(), url))
                .isPresent();
        if (!urlExists) {
            createRadioStationStreamUrl.handle(
                    new CreateRadioStationStreamUrl(stream.getRadioStationId(), stream.getId(), type, url)
            );
        }
    }

}