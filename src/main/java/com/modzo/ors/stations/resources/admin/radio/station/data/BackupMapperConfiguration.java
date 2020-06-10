package com.modzo.ors.stations.resources.admin.radio.station.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class BackupMapperConfiguration {

    public static ObjectWriter constructWriter() {
        ObjectMapper mapper = mapper();
        return mapper.writer();
    }

    public static ObjectReader constructReader() {
        return mapper().reader().forType(BackupData.class);
    }

    private static ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
