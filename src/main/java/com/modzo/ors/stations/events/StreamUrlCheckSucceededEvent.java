package com.modzo.ors.stations.events;

import org.springframework.context.ApplicationEvent;

public class StreamUrlCheckSucceededEvent extends ApplicationEvent {

    private final long radioStationId;

    private final long streamId;

    public StreamUrlCheckSucceededEvent(Object source, long radioStationId, long streamId) {
        super(source);
        this.radioStationId = radioStationId;
        this.streamId = streamId;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public long getStreamId() {
        return streamId;
    }
}
