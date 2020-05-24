package com.modzo.ors.stations.resources.admin.radio.station.importer;

class ImportEntry {

    private String radioStationName;

    private String streamUrl;

    public ImportEntry() {
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
