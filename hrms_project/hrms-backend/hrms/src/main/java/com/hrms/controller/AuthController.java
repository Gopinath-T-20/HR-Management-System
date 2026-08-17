package com.hrms.controller;

import com.hrms.dto.LoginRequestDTO;
import com.hrms.dto.LoginResponseDTO;
import com.hrms.entity.Employee;
import com.hrms.security.JwtUtil;
import com.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {

        Employee emp = service.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (emp == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(emp.getEmpId(), emp.getUsername(), emp.getRole());

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                emp.getEmpId(),
                emp.getEmpName(),
                emp.getUsername(),
                emp.getRole(),
                emp.getEmail(),
                emp.getDesignation(),
                emp.getPhotoUrl()
        );

        return ResponseEntity.ok(response);
    }
}