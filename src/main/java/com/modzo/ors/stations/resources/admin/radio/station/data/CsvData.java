package com.modzo.ors.stations.resources.admin.radio.station.data;

public class CsvData {

    private String radioStationUniqueId;

    private String radioStationName;

    private boolean radioStationEnabled;

    private String streamUrls;

    private String streamIsWorking;

    public CsvData() {
    }

    public CsvData(String radioStationUniqueId,
                   String radioStationName,
                   boolean radioStationEnabled,
                   String streamUrls,
                   String streamIsWorking) {
        this.radioStationUniqueId = radioStationUniqueId;
        this.radioStationName = radioStationName;
        this.radioStationEnabled = radioStationEnabled;
        this.streamUrls = streamUrls;
        this.streamIsWorking = streamIsWorking;
    }

    public String getRadioStationUniqueId() {
        return radioStationUniqueId;
    }

    public String getRadioStationName() {
        return radioStationName;
    }

    public void setRadioStationName(String radioStationName) {
        this.radioStationName = radioStationName;
    }

    public boolean isRadioStationEnabled() {
        return radioStationEnabled;
    }

    public void setRadioStationEnabled(boolean radioStationEnabled) {
        this.radioStationEnabled = radioStationEnabled;
    }

    public String getStreamUrls() {
        return streamUrls;
    }

    public void setStreamUrls(String streamUrls) {
        this.streamUrls = streamUrls;
    }

    public String getStreamIsWorking() {
        return streamIsWorking;
    }

    public void setStreamIsWorking(String streamIsWorking) {
        this.streamIsWorking = streamIsWorking;
    }
}
