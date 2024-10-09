package com.test.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attestation-reussite")
public class Controller {

    @Autowired
    private JasperService service;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> generateForDownload(
            @PathVariable String id,
            @RequestParam(defaultValue = "en") String lang) { // Default language set to English
        return generateReport(id, lang, true);
    }

    @GetMapping("/{id}/display")
    public ResponseEntity<byte[]> generateForDisplay(
            @PathVariable String id,
            @RequestParam(defaultValue = "fr") String lang) { // Default language set to English
        return generateReport(id, lang, false);
    }

    private ResponseEntity<byte[]> generateReport(String id, String lang, boolean isDownload) {
        try {
            byte[] pdfBytes = service.generateAttestationReussite(id, lang); // Pass language parameter

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            if (isDownload) {
                headers.setContentDisposition(ContentDisposition.builder("attachment")
                        .filename("attestation_reussite_" + id + ".pdf").build());
            } else {
                headers.setContentDisposition(ContentDisposition.builder("inline")
                        .filename("attestation_reussite_" + id + ".pdf").build());
            }

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
