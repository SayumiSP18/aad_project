package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private long customerId;
    private Long userId;
    private String username;

//    @NotBlank(message = "Full name is required")
    private String fullName;

//    @NotBlank(message = "Address is required")
    private String address;}
