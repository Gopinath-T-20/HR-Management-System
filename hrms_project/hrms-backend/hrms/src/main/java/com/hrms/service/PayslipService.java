package com.hrms.service;

import com.hrms.dto.PayslipRequestDTO;
import com.hrms.entity.Employee;
import com.hrms.entity.Payslip;
import com.hrms.repository.EmployeeRepository;
import com.hrms.repository.PayslipRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    // HR - Payslip generate
    public Payslip generatePayslip(Integer empId, PayslipRequestDTO request) {

        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        if (payslipRepo.existsByEmployee_EmpIdAndMonthAndYear(empId, request.getMonth(), request.getYear())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Payslip already generated for " + request.getMonth() + " " + request.getYear());
        }

        double basic = emp.getSalary();
        double net = basic + request.getAllowances() - request.getDeductions();

        if (net < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deductions cannot exceed earnings");
        }

        Payslip payslip = new Payslip();
        payslip.setEmployee(emp);
        payslip.setMonth(request.getMonth());
        payslip.setYear(request.getYear());
        payslip.setBasicSalary(basic);
        payslip.setAllowances(request.getAllowances());
        payslip.setDeductions(request.getDeductions());
        payslip.setNetSalary(net);
        payslip.setGeneratedDate(LocalDate.now());

        return payslipRepo.save(payslip);
    }

    public List<Payslip> getPayslipsByEmployee(Integer empId) {
        return payslipRepo.findByEmployee_EmpId(empId);
    }

    // To generate PDF
    public byte[] generatePdf(Integer payslipId) {

        Payslip p = payslipRepo.findById(payslipId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payslip not found"));

        try (PDDocument doc = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {

                float margin = 50;
                float y = page.getMediaBox().getHeight() - 60;

                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                cs.beginText();
                cs.newLineAtOffset(margin, y);
                cs.showText("HRMS - Payslip");
                cs.endText();

                y -= 30;
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);

                String[] lines = {
                        "Employee Name : " + p.getEmployee().getEmpName(),
                        "Employee ID   : " + p.getEmployee().getEmpId(),
                        "Designation   : " + p.getEmployee().getDesignation(),
                        "Pay Period    : " + p.getMonth() + " " + p.getYear(),
                        " ",
                        "Basic Salary  : " + String.format("%.2f", p.getBasicSalary()),
                        "Allowances    : " + String.format("%.2f", p.getAllowances()),
                        "Deductions    : " + String.format("%.2f", p.getDeductions()),
                        " ",
                        "Net Salary    : " + String.format("%.2f", p.getNetSalary()),
                        " ",
                        "Generated On  : " + p.getGeneratedDate()
                };

                for (String line : lines) {
                    y -= 20;
                    cs.beginText();
                    cs.newLineAtOffset(margin, y);
                    cs.showText(line);
                    cs.endText();
                }
            }

            doc.save(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate PDF");
        }
    }
}