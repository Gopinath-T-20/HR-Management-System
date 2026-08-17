package com.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private Integer empId;
    private String empName;
    private String username;
    private String role;
    private String email;
    private String designation;
    private String photoUrl;
}