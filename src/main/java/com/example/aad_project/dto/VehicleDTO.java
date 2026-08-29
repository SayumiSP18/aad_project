package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private long vehicleId;

//    @NotNull(message = "Driver is required")
    private Long driverId;

    private String driverUsername;

//    @NotBlank(message = "Vehicle number is required")
    private String vehicleNo;

//    @NotBlank(message = "Vehicle type is required")
    private String type;

//    @Positive(message = "Capacity must be greater than 0")
    private double capacityKg;

    public VehicleDTO(long vehicleId, long driverId, String driverUsername, String vehicleNo, String type, double capacityKg) {
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.driverUsername = driverUsername;
        this.vehicleNo = vehicleNo;
        this.type = type;
        this.capacityKg = capacityKg;
    }}
