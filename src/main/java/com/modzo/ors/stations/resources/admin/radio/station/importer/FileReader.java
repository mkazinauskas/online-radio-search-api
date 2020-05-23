package com.modzo.ors.stations.resources.admin.radio.station.importer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Component
class FileReader {

    private static final ObjectReader READER = constructReader();

    List<ImportEntry> read(MultipartFile file) {
        InputStream input = Sneaky.sneak(file::getInputStream);
        MappingIterator<ImportEntry> readValues = Sneaky.sneak(() -> READER.readValues(input));
        return Sneaky.sneak(readValues::readAll);
    }

    private static ObjectReader constructReader() {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readerFor(ImportEntry.class)
                .with(bootstrapSchema);
    }
}
