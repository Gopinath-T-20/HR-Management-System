package com.hrms.service;

import com.hrms.entity.Attendance;
import com.hrms.entity.Employee;
import com.hrms.repository.AttendanceRepository;
import com.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public String markAttendance(Integer empId) {

        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        LocalDate today = LocalDate.now();

        Attendance existing = attendanceRepo.findByEmployee_EmpIdAndAttendanceDate(empId, today);

        if (existing != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already marked for today!");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(emp);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalTime.now());
        attendance.setStatus("Present");

        attendanceRepo.save(attendance);

        return "Attendance marked successfully!";
    }

    public List<Attendance> getAttendanceByEmployee(Integer empId) {
        return attendanceRepo.findByEmployee_EmpId(empId);
    }
}