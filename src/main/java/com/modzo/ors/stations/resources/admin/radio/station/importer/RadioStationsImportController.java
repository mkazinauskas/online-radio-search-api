package com.modzo.ors.stations.resources.admin.radio.station.importer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationsImportController {

    private final ImporterService importerService;

    RadioStationsImportController(ImporterService importerService) {
        this.importerService = importerService;
    }

    @PostMapping("/admin/radio-stations/importer")
    ResponseEntity<String> radioStationsImport(@RequestParam("file") MultipartFile file) {
        importerService.run(file);
        return ok().build();
    }
}
