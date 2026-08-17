package com.hrms.service;

import com.hrms.entity.Employee;
import com.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee emp) {
        if (repo.findByUsername(emp.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        emp.setPassword(encoder.encode(emp.getPassword()));
        return repo.save(emp);
    }

    public Employee updateEmployee(Integer id, Employee emp) {
        Employee existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        Employee usernameOwner = repo.findByUsername(emp.getUsername());
        if (usernameOwner != null && !usernameOwner.getEmpId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        emp.setEmpId(id);
        if (emp.getPassword() != null && !emp.getPassword().isEmpty()) {
            emp.setPassword(encoder.encode(emp.getPassword()));
        } else {
            emp.setPassword(existing.getPassword());
        }

        if (emp.getPhotoUrl() == null || emp.getPhotoUrl().isEmpty()) {
            emp.setPhotoUrl(existing.getPhotoUrl());
        }

        return repo.save(emp);
    }

    public void deleteEmployee(Integer id) {
        repo.deleteById(id);
    }

    public Employee login(String username, String rawPassword) {
        Employee emp = repo.findByUsername(username);

        if (emp != null && encoder.matches(rawPassword, emp.getPassword())) {
            return emp;
        }
        return null;
    }

    public Employee uploadPhoto(Integer id, MultipartFile file) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only JPG or PNG images are allowed");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String extension = contentType.equals("image/png") ? ".png" : ".jpg";
            String fileName = "emp_" + id + "_" + UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            emp.setPhotoUrl("/photos/" + fileName);
            return repo.save(emp);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload photo");
        }
    }
}