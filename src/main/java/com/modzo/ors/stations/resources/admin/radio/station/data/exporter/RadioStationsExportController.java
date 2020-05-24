package com.modzo.ors.stations.resources.admin.radio.station.data.exporter;

import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RadioStationsExportController {

    private final ExporterService exporterService;

    RadioStationsExportController(ExporterService exporterService) {
        this.exporterService = exporterService;
    }

    @GetMapping("/admin/radio-stations/exporter")
    ResponseEntity<Resource> radioStationsExport(Pageable pageable) {
        Resource resource = exporterService.export(pageable);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName(pageable));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(Sneaky.sneak(resource::contentLength))
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private String fileName(Pageable pageable) {
        return String.format("export-%s-%s", pageable.getPageNumber(), pageable.getPageSize());
    }
}
