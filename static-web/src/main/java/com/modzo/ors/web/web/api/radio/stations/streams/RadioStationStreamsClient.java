package com.modzo.ors.web.web.api.radio.stations.streams;

import com.modzo.ors.web.web.api.RestPageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "radioStreamsClient" +
//        "", url = "${application.apiUrl}")
public interface RadioStationStreamsClient {

    @GetMapping("/radio-stations/{id}/streams")
    RestPageImpl<RadioStationStreamResponse> getRadioStationStreams(@PathVariable("id") long id);

    @GetMapping("/radio-stations/{radioStationId}/streams/{streamId}")
    RadioStationStreamResponse getRadioStationStream(@PathVariable("radioStationId") long radioStationId,
                                                     @PathVariable("streamId") long streamId);
}
