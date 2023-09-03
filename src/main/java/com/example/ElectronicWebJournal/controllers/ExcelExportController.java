package com.example.ElectronicWebJournal.controllers;

import com.example.ElectronicWebJournal.dto.for_excel.ExcelData;
import com.example.ElectronicWebJournal.services.ExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelExportController {
    private final ExcelExportService excelExportService;

    @PostMapping("/export-to-excel")
    public ResponseEntity<Resource> exportToExcel(@RequestBody ExcelData excel) {
        byte[] excelData = excelExportService.exportToExcel(excel.getSubjectId(), excel.getGroupId());

        ByteArrayResource resource = new ByteArrayResource(excelData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=grades.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelData.length)
                .body(resource);
    }
}
