package com.modzo.ors.stations.resources.admin.radio.station.data;

public class CsvData {

    private String radioStationName;

    private String streamUrls;

    public CsvData() {
    }

    public CsvData(String radioStationName, String streamUrls) {
        this.radioStationName = radioStationName;
        this.streamUrls = streamUrls;
    }

    public String getRadioStationName() {
        return radioStationName;
    }

    public void setRadioStationName(String radioStationName) {
        this.radioStationName = radioStationName;
    }

    public String getStreamUrls() {
        return streamUrls;
    }

    public void setStreamUrls(String streamUrls) {
        this.streamUrls = streamUrls;
    }
}
