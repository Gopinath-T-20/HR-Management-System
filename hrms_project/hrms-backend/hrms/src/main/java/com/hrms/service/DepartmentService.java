package com.hrms.service;

import com.hrms.entity.Department;
import com.hrms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repo;

    public List<Department> getAllDepartments() {
        return repo.findAll();
    }

    public Department saveDepartment(Department dept) {
        return repo.save(dept);
    }
}
