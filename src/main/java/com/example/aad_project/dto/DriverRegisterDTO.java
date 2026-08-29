package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRegisterDTO {

//    @NotBlank(message = "Username is required")
//    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

//    @NotBlank(message = "Password is required")
//    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

//    @NotNull(message = "Branch is required")
    private Long branchId;

//    @NotBlank(message = "License number is required")
    private String licenseNo;
}
