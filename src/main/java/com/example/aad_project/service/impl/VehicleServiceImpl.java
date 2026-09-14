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
        try {
            Driver driver = driverRepository.findById(vehicleDTO.getDriverId())
                    .orElseThrow(() -> new CustomException(404, "Driver not found"));

            Vehicle vehicle = new Vehicle();
            vehicle.setDriver(driver);
            vehicle.setVehicleNo(vehicleDTO.getVehicleNo());
            vehicle.setType(vehicleDTO.getType());
            vehicle.setCapacityKg(vehicleDTO.getCapacityKg());
            vehicleRepository.save(vehicle);
            log.info("New vehicle registered: {}", vehicle.getVehicleNo());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save vehicle '{}': {}", vehicleDTO.getVehicleNo(), e.getMessage(), e);
            throw new CustomException(500, "Failed to register vehicle");
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        try {
            return vehicleRepository.getAllVehicles();
        } catch (Exception e) {
            log.error("Failed to fetch vehicles: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch vehicles");
        }
    }

    @Override
    public List<VehicleDTO> filterVehicles(Long driverId) {
        try {
            return vehicleRepository.filterVehicles(driverId);
        } catch (Exception e) {
            log.error("Failed to filter vehicles by driver {}: {}", driverId, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter vehicles");
        }
    }

    @Override
    public VehicleDTO selectVehicle(long vehicleId) {
        try {
            return vehicleRepository.selectVehicle(vehicleId)
                    .orElseThrow(() -> new CustomException(404, "Vehicle not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch vehicle {}: {}", vehicleId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch vehicle");
        }
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
        try {
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
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update vehicle {}: {}", vehicleDTO.getVehicleId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update vehicle");
        }
    }

    @Override
    public void deleteVehicle(long vehicleId) {
        try {
            if (!vehicleRepository.existsById(vehicleId))
                throw new CustomException(404, "Vehicle not found");
            vehicleRepository.deleteById(vehicleId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete vehicle {}: {}", vehicleId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete vehicle");
        }
    }
}