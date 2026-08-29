package com.example.aad_project.service;

import com.example.aad_project.dto.DriverDTO;
import com.example.aad_project.dto.DriverRegisterDTO;

import java.util.List;

public interface DriverSrvice {
    void registerDriver(DriverRegisterDTO registerDTO);

    List<DriverDTO> getAllDrivers();

    List<DriverDTO> filterDrivers(Long branchId);

    DriverDTO selectDriver(long driverId);

    DriverDTO getMyProfile(String username);

    void updateDriver(DriverDTO driverDTO);

    void deleteDriver(long driverId);

}
