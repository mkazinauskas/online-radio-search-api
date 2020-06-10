package com.modzo.ors.stations.resources.admin.radio.station.data.importer;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.modzo.ors.stations.resources.admin.radio.station.data.BackupData;
import com.modzo.ors.stations.resources.admin.radio.station.data.BackupMapperConfiguration;
import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

class JsonReader {

    private static final ObjectReader READER = BackupMapperConfiguration.constructReader();

    static List<BackupData> read(MultipartFile file) {
        InputStream input = Sneaky.sneak(file::getInputStream);
        MappingIterator<BackupData> lines = Sneaky.sneak(() -> READER.readValues(input));
        return Sneaky.sneak(lines::readAll);
    }

}
