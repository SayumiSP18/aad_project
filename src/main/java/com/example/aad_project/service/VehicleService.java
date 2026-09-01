package com.example.aad_project.service;

import com.example.aad_project.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);

    List<VehicleDTO> getAllVehicles();

    List<VehicleDTO> filterVehicles(Long driverId);

    VehicleDTO selectVehicle(long vehicleId);

    void updateVehicle(VehicleDTO vehicleDTO);

    void deleteVehicle(long vehicleId);
}
