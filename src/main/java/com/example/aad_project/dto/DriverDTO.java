//package com.example.aad_project.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class DriverDTO {
//    private Long id;
//    private Long userId;
//    private String fullName;
//    private String phoneNumber;
//    private String licenseNumber;
//    private LocalDate licenseExpiry;
//    private String currentVehiclePlate;
//    private String branchName;
//    private Boolean available;
//}

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
    private Long id;                 // keep for other endpoints
    private Long driverId;           // frontend uses this
    private Long userId;
    private String username;         // ← missing – add this
    private String fullName;
    private String phoneNumber;
    private String licenseNo;        // ← rename from licenseNumber
    private String licenseNumber;    // (optional – keep for compatibility)
    private LocalDate licenseExpiry;
    private String currentVehiclePlate;
    private String branchName;
    private Boolean available;
}
