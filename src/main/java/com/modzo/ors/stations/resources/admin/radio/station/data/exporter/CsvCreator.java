package com.modzo.ors.stations.resources.admin.radio.station.data.exporter;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvMapperConfiguration;
import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
class CsvCreator {

    private static final ObjectWriter WRITER = CsvMapperConfiguration.constructWriter();

    static byte[] write(List<CsvData> data) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Sneaky.sneaked(() -> WRITER.writeValue(stream, data)).run();
        return stream.toByteArray();
    }

}
