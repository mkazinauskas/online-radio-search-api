package com.modzo.ors.stations.resources.admin.radio.station.stream.update;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateRadioStationStreamRequest {

    @NotBlank
    @URL
    private String url;

    @Min(0L)
    private Integer bitRate;

    private RadioStationStream.Type type;

    @NotNull
    private Boolean working;

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

    public RadioStationStream.Type getType() {
        return type;
    }

    public void setType(RadioStationStream.Type type) {
        this.type = type;
    }

    public Boolean isWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }
}
