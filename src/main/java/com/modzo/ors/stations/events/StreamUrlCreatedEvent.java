package com.modzo.ors.stations.events;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.springframework.context.ApplicationEvent;

public class StreamUrlCreatedEvent extends ApplicationEvent {

    private final StreamUrl.Type type;

    private final long radioStationId;

    private final long streamId;

    public StreamUrlCreatedEvent(Object source,
                                 StreamUrl.Type type,
                                 long radioStationId,
                                 long streamId) {
        super(source);
        this.type = type;
        this.radioStationId = radioStationId;
        this.streamId = streamId;
    }

    public StreamUrl.Type getType() {
        return type;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public long getStreamId() {
        return streamId;
    }
}
