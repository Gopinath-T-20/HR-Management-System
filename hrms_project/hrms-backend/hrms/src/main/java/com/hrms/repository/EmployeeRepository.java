package com.hrms.repository;

import com.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

    Employee findByUsernameAndPassword(String username, String password);

    Employee findByUsername(String username);
}
