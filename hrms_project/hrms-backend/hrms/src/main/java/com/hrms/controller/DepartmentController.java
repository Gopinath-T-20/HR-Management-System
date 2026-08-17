package com.hrms.controller;

import com.hrms.entity.Department;
import com.hrms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @GetMapping
    public List<Department> getAll() {
        return service.getAllDepartments();
    }

    @PostMapping
    public Department create(@RequestBody Department dept) {
        return service.saveDepartment(dept);
    }
}