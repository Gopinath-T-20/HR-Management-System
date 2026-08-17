package com.hrms.repository;

import com.hrms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByEmployee_EmpId(Integer empId);

    Attendance findByEmployee_EmpIdAndAttendanceDate(Integer empId, LocalDate date);
}