package com.hrms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "LeaveRequest")
@Data
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveId;

    @NotNull(message = "Employee is required")
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @NotNull(message = "From date is required")
    @FutureOrPresent(message = "From date cannot be a past date")
    private LocalDate fromDate;

    @NotNull(message = "To date is required")
    private LocalDate toDate;

    @NotBlank(message = "Reason is required")
    @Size(min = 5, max = 250, message = "Reason must be between 5 and 250 characters")
    private String reason;

    @Pattern(regexp = "^(Pending|Approved|Rejected)$", message = "Status must be Pending, Approved or Rejected")
    private String status;   // "Pending" / "Approved" / "Rejected"
}