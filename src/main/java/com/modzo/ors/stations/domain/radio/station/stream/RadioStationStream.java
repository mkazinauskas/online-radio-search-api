package com.modzo.ors.stations.domain.radio.station.stream;

import com.modzo.ors.stations.domain.radio.station.RadioStation;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Entity
@Table(name = "radio_station_streams")
public class RadioStationStream {

    public enum Type {
        MP3, ACC, MPEG, UNKNOWN
    }

    @Id
    @GeneratedValue(generator = "radio_station_streams_sequence", strategy = SEQUENCE)
    @SequenceGenerator(
            name = "radio_station_streams_sequence",
            sequenceName = "radio_station_streams_sequence",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id", unique = true, nullable = false)
    private UUID uniqueId = UUID.randomUUID();

    @Column(name = "created", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "radio_station_id")
    private RadioStation radioStation;

    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "bit_rate")
    private Integer bitRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "working")
    private boolean working = true;

    @Column(name = "checked")
    private ZonedDateTime checked;

    @OneToMany(mappedBy = "stream", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @MapKey(name = "type")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<StreamUrl.Type, StreamUrl> urls;

    RadioStationStream() {
    }

    public RadioStationStream(RadioStation radioStation, String url) {
        this.radioStation = radioStation;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public RadioStation getRadioStation() {
        return radioStation;
    }

    public long getRadioStationId() {
        return radioStation.getId();
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

    public Optional<Type> getType() {
        return Optional.ofNullable(type);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public ZonedDateTime getChecked() {
        return checked;
    }

    public void setChecked(ZonedDateTime checked) {
        this.checked = checked;
    }

    public Map<StreamUrl.Type, StreamUrl> getUrls() {
        return urls;
    }

    public void setUrls(Map<StreamUrl.Type, StreamUrl> urls) {
        this.urls = urls;
    }

    public Optional<StreamUrl> findUrl(StreamUrl.Type type) {
        return Optional.ofNullable(this.getUrls().get(type));
    }
}
