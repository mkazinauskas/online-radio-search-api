package com.mozdzo.ors.domain.radio.station.stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "radio_station_streams")
public class RadioStationStream {
    @Id
    @GeneratedValue(generator = "radio_station_streams_sequence", strategy = SEQUENCE)
    @SequenceGenerator(
            name = "radio_station_streams_sequence",
            sequenceName = "radio_station_streams_sequence",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "radio_station_id")
    private long radioStationId;

    @Column(name = "url", length = 100)
    private String url;

    RadioStationStream() {
    }

    public RadioStationStream(long radioStationId, String url) {
        this.radioStationId = radioStationId;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public long getRadioStationId() {
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
