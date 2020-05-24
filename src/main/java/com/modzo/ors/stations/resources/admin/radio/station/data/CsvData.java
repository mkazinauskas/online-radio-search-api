package com.modzo.ors.stations.resources.admin.radio.station.data;

public class CsvData {

    private String radioStationName;

    private String streamUrl;

    public CsvData() {
    }

    public CsvData(String radioStationName, String streamUrl) {
        this.radioStationName = radioStationName;
        this.streamUrl = streamUrl;
    }

    public String getRadioStationName() {
        return radioStationName;
    }

    public void setRadioStationName(String radioStationName) {
        this.radioStationName = radioStationName;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }
}
