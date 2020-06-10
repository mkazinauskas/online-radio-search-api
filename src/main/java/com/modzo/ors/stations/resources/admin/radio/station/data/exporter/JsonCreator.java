package com.modzo.ors.stations.resources.admin.radio.station.data.exporter;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.modzo.ors.stations.resources.admin.radio.station.data.BackupData;
import com.modzo.ors.stations.resources.admin.radio.station.data.BackupMapperConfiguration;
import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
class JsonCreator {

    private static final ObjectWriter WRITER = BackupMapperConfiguration.constructWriter();

    static byte[] write(List<BackupData> data) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Sneaky.sneaked(() -> WRITER.writeValue(stream, data)).run();
        return stream.toByteArray();
    }

}
