package com.hrms.controller;

import com.hrms.entity.Leave;
import com.hrms.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // To Apply Leave
    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody @Valid Leave leave) {
        if (leave.getEmployee() == null || leave.getEmployee().getEmpId() == null) {
            return ResponseEntity.badRequest().body("Employee id is required");
        }
        return ResponseEntity.ok(leaveService.applyLeave(leave.getEmployee().getEmpId(), leave));
    }

    // To see employee leave history
    @GetMapping("/employee/{empId}")
    public List<Leave> getLeavesByEmployee(@PathVariable Integer empId) {
        return leaveService.getLeavesByEmployee(empId);
    }

    // HR to see the leave request
    @GetMapping("/all")
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    //  To Update Leave status (Approve/Reject)
    @PutMapping("/status/{leaveId}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer leaveId, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return ResponseEntity.ok(leaveService.updateLeaveStatus(leaveId, status));
    }
}