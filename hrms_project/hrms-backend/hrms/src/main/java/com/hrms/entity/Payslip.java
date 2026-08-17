package com.hrms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "Payslip", uniqueConstraints = @UniqueConstraint(columnNames = {"emp_id", "month", "year"}))
@Data
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer payslipId;

    @NotNull(message = "Employee is required")
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @NotBlank(message = "Month is required")
    private String month;      // e.g. "January"

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Basic salary is required")
    @Positive
    private Double basicSalary;

    @NotNull(message = "Allowances is required")
    @PositiveOrZero
    private Double allowances;

    @NotNull(message = "Deductions is required")
    @PositiveOrZero
    private Double deductions;

    private Double netSalary;

    private LocalDate generatedDate;
}