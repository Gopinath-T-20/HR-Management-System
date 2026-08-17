package com.hrms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PayslipRequestDTO {

    @NotBlank(message = "Month is required")
    private String month;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Allowances is required")
    @PositiveOrZero
    private Double allowances;

    @NotNull(message = "Deductions is required")
    @PositiveOrZero
    private Double deductions;
}