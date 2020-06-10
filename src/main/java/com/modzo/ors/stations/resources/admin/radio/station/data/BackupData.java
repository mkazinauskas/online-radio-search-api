package com.modzo.ors.stations.resources.admin.radio.station.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public class BackupData {

    @Validated
    public static class Stream {

        @NotNull
        @URL
        private final String url;

        @NotNull
        private final Boolean working;

        @JsonCreator
        public Stream(@JsonProperty("url") String url,
                      @JsonProperty("working") Boolean working) {
            this.url = url;
            this.working = working;
        }

        public String getUrl() {
            return url;
        }

        public boolean isWorking() {
            return working;
        }
    }

    private final String uniqueId;

    @NotNull
    private final String title;

    @NotNull
    private final Boolean enabled;

    @NotNull
    @Valid
    private final List<Stream> streams;

    @JsonCreator
    public BackupData(
            @JsonProperty("uniqueId") String uniqueId,
            @JsonProperty("title") String title,
            @JsonProperty("enabled") boolean enabled,
            @JsonProperty("streams") List<Stream> streams) {
        this.uniqueId = uniqueId;
        this.title = title;
        this.enabled = enabled;
        this.streams = streams;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<Stream> getStreams() {
        return streams;
    }
}
