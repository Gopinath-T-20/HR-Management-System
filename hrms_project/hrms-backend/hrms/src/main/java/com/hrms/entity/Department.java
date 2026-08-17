package com.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deptId;

    private String deptName;
}
