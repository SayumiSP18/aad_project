package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO {

//    @NotBlank(message = "Username is required")
//    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

//    @NotBlank(message = "Password is required")
//    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

//    @NotBlank(message = "Full name is required")
    private String fullName;

//    @NotBlank(message = "Address is required")
    private String address;}
