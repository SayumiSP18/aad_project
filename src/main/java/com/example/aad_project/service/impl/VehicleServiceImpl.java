package com.example.aad_project.service.impl;

import com.example.aad_project.dto.VehicleDTO;
import com.example.aad_project.entity.Driver;
import com.example.aad_project.entity.Vehicle;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.DriverRepository;
import com.example.aad_project.repository.VehicleRepository;
import com.example.aad_project.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        Driver driver = driverRepository.findById(vehicleDTO.getDriverId())
                .orElseThrow(() -> new CustomException(404, "Driver not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setDriver(driver);
        vehicle.setVehicleNo(vehicleDTO.getVehicleNo());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setCapacityKg(vehicleDTO.getCapacityKg());
        vehicleRepository.save(vehicle);
        log.info("New vehicle registered: {}", vehicle.getVehicleNo());
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.getAllVehicles();
    }

    @Override
    public List<VehicleDTO> filterVehicles(Long driverId) {
        return vehicleRepository.filterVehicles(driverId);
    }

    @Override
    public VehicleDTO selectVehicle(long vehicleId) {
        return vehicleRepository.selectVehicle(vehicleId)
                .orElseThrow(() -> new CustomException(404, "Vehicle not found"));
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(vehicleDTO.getVehicleId())
                .orElseThrow(() -> new CustomException(404, "Vehicle not found"));

        if (vehicleDTO.getDriverId() != null) {
            Driver driver = driverRepository.findById(vehicleDTO.getDriverId())
                    .orElseThrow(() -> new CustomException(404, "Driver not found"));
            vehicle.setDriver(driver);
        }

        vehicle.setVehicleNo(vehicleDTO.getVehicleNo());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setCapacityKg(vehicleDTO.getCapacityKg());
        vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(long vehicleId) {
        if (!vehicleRepository.existsById(vehicleId))
            throw new CustomException(404, "Vehicle not found");
        vehicleRepository.deleteById(vehicleId);
    }}
