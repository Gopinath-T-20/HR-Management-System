package com.hrms.controller;

import com.hrms.entity.Attendance;
import com.hrms.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // To Attendance mark
    @PostMapping("/mark/{empId}")
    public String markAttendance(@PathVariable Integer empId) {
        return attendanceService.markAttendance(empId);
    }

    // To see employee attendace history
    @GetMapping("/employee/{empId}")
    public List<Attendance> getAttendanceByEmployee(@PathVariable Integer empId) {
        return attendanceService.getAttendanceByEmployee(empId);
    }
}
