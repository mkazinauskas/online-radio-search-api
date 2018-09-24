package com.mozdzo.ors.domain.events;

import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_CREATED;

public class RadioStationCreated extends DomainEvent {

    private final String uniqueId;

    private final String title;

    public RadioStationCreated(Object source, String uniqueId, String title) {
        super(source);
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_CREATED;
    }
}
