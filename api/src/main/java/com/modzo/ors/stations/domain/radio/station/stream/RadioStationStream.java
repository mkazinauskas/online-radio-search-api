package com.modzo.ors.stations.domain.radio.station.stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

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

    @Column(name = "unique_id", length = 20, unique = true, nullable = false)
    private String uniqueId = randomAlphanumeric(20);

    @Column(name = "radio_station_id")
    private long radioStationId;

    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "bit_rate")
    private Integer bitRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    RadioStationStream() {
    }

    public RadioStationStream(long radioStationId, String url) {
        this.radioStationId = radioStationId;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
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

    public Integer getBitRate() {
        return bitRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        MP3, ACC, MPEG, UNKNOWN
    }
}
