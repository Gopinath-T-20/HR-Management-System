package com.hrms.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

import java.time.LocalDate;

@Entity
@Table(name = "Attendance")
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private String status;  // "persent" / "Absent"
}
