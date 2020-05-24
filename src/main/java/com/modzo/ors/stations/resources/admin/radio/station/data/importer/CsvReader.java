package com.modzo.ors.stations.resources.admin.radio.station.data.importer;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvData;
import com.modzo.ors.stations.resources.admin.radio.station.data.CsvMapperConfiguration;
import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

class CsvReader {

    private static final ObjectReader READER = CsvMapperConfiguration.constructReader();

    static List<CsvData> read(MultipartFile file) {
        InputStream input = Sneaky.sneak(file::getInputStream);
        MappingIterator<CsvData> readValues = Sneaky.sneak(() -> READER.readValues(input));
        return Sneaky.sneak(readValues::readAll);
    }

}
