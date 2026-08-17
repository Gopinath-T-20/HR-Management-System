package com.hrms.repository;

import com.hrms.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    List<Leave> findByEmployee_EmpId(Integer empId);
}
