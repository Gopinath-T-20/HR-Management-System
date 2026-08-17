package com.hrms.service;

import com.hrms.entity.Employee;
import com.hrms.entity.Leave;
import com.hrms.repository.EmployeeRepository;
import com.hrms.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    // To Apply Leave
    public Leave applyLeave(Integer empId, Leave leaveRequest) {

        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        if (leaveRequest.getToDate().isBefore(leaveRequest.getFromDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "To date cannot be before from date");
        }

        leaveRequest.setEmployee(emp);
        leaveRequest.setStatus("Pending");

        return leaveRepo.save(leaveRequest);
    }

    // To see employee leave history
    public List<Leave> getLeavesByEmployee(Integer empId) {
        return leaveRepo.findByEmployee_EmpId(empId);
    }

    // Hr to see the leave request
    public List<Leave> getAllLeaves() {
        return leaveRepo.findAll();
    }

    // To Update leave status (Approve/Reject)
    public Leave updateLeaveStatus(Integer leaveId, String status) {

        if (status == null || !(status.equals("Pending") || status.equals("Approved") || status.equals("Rejected"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be Pending, Approved or Rejected");
        }

        Leave leave = leaveRepo.findById(leaveId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave request not found"));

        leave.setStatus(status);
        return leaveRepo.save(leave);
    }
}