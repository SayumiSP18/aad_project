package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private Long id;
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String licenseNumber;
    private LocalDate licenseExpiry;
    private String currentVehiclePlate;
    private String branchName;
    private boolean available;
}
