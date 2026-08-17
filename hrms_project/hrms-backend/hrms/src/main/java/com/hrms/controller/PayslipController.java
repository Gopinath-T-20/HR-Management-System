package com.hrms.controller;

import com.hrms.dto.PayslipRequestDTO;
import com.hrms.entity.Payslip;
import com.hrms.service.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/payslips")
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @PostMapping("/generate/{empId}")
    public ResponseEntity<?> generatePayslip(@PathVariable Integer empId, @RequestBody @Valid PayslipRequestDTO request) {
        Payslip payslip = payslipService.generatePayslip(empId, request);
        return ResponseEntity.ok(payslip);
    }

    @GetMapping("/employee/{empId}")
    public List<Payslip> getPayslipsByEmployee(@PathVariable Integer empId) {
        return payslipService.getPayslipsByEmployee(empId);
    }

    // PDF download
    @GetMapping("/download/{payslipId}")
    public ResponseEntity<byte[]> downloadPayslip(@PathVariable Integer payslipId) {

        byte[] pdfBytes = payslipService.generatePdf(payslipId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payslip_" + payslipId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}