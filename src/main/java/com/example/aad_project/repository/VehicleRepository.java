package com.example.aad_project.repository;

import com.example.aad_project.dto.VehicleDTO;
import com.example.aad_project.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.VehicleDTO(v.vehicleId, v.driver.driverId, v.driver.user.username, " +
            "v.vehicleNo, v.type, v.capacityKg) FROM Vehicle v")
    List<VehicleDTO> getAllVehicles();

    @Query(value = "SELECT new com.example.aad_project.dto.VehicleDTO(v.vehicleId, v.driver.driverId, v.driver.user.username, " +
            "v.vehicleNo, v.type, v.capacityKg) FROM Vehicle v WHERE v.vehicleId = :vehicleId")
    Optional<VehicleDTO> selectVehicle(@Param("vehicleId") long vehicleId);

    @Query(value = "SELECT new com.example.aad_project.dto.VehicleDTO(v.vehicleId, v.driver.driverId, v.driver.user.username, " +
            "v.vehicleNo, v.type, v.capacityKg) FROM Vehicle v " +
            "WHERE (:driverId IS NULL OR v.driver.driverId = :driverId)")
    List<VehicleDTO> filterVehicles(@Param("driverId") Long driverId);
}
