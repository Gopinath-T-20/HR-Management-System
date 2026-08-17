package com.hrms.repository;

import com.hrms.entity.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayslipRepository extends JpaRepository<Payslip, Integer> {
    List<Payslip> findByEmployee_EmpId(Integer empId);
    boolean existsByEmployee_EmpIdAndMonthAndYear(Integer empId, String month, Integer year);
}