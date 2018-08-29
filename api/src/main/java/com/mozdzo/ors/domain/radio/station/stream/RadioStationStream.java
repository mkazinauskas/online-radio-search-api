package com.mozdzo.ors.domain.radio.station.stream;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "radio_station_streams")
public class RadioStationStream {
    @Id
    @GeneratedValue(generator = "radio_station_streams_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "radio_station_streams_sequence", sequenceName = "radio_station_streams_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "radio_station_id")
    private Long radioStationId;

    @Column(name = "url", length = 100)
    private String url;

    RadioStationStream() {
    }

    public RadioStationStream(Long radioStationId, String url) {
        this.radioStationId = radioStationId;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRadioStationId() {
        return radioStationId;
    }

    public void setRadioStationId(Long radioStationId) {
        this.radioStationId = radioStationId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
