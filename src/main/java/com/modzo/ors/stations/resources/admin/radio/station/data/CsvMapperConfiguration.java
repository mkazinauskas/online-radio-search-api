package com.modzo.ors.stations.resources.admin.radio.station.data;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvMapperConfiguration {

    public static ObjectWriter constructWriter() {
        CsvMapper mapper = mapper();
        return mapper.writer(schema(mapper));
    }

    public static ObjectReader constructReader() {
        CsvMapper mapper = mapper();
        return mapper.reader(schema(mapper))
                .forType(CsvData.class);
    }

    private static CsvMapper mapper() {
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        mapper.enable(CsvParser.Feature.FAIL_ON_MISSING_COLUMNS);
        mapper.enable(CsvParser.Feature.EMPTY_STRING_AS_NULL);
        return mapper;
    }

    private static CsvSchema schema(CsvMapper mapper) {
        return mapper.schemaFor(CsvData.class)
                .withLineSeparator("\n")
                .withHeader();
    }

}
