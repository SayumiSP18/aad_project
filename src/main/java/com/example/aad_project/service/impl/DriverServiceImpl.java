package com.example.aad_project.service.impl;

import com.example.aad_project.dto.DriverDTO;
import com.example.aad_project.dto.DriverRegisterDTO;
import com.example.aad_project.entity.Branch;
import com.example.aad_project.entity.Driver;
import com.example.aad_project.entity.User;
//import com.example.aad_project.enumeration.UserRole;
import com.example.aad_project.entity.Role;
import com.example.aad_project.repository.RoleRepository;
import com.example.aad_project.repository.BranchRepository;
import com.example.aad_project.repository.DriverRepository;
import com.example.aad_project.repository.UserRepository;
import com.example.aad_project.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void registerDriver(DriverRegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username already exists"
            );
        }

        Branch branch = branchRepository.findById(registerDTO.getBranchId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Branch not found"
                ));

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Role driverRole = roleRepository.findByRoleName("DRIVER")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "DRIVER role not found"
                ));

        user.setUserRoles(driverRole);
        User savedUser = userRepository.save(user);

        Driver driver = new Driver();
        driver.setUser(savedUser);
        driver.setBranch(branch);
        driver.setLicenseNo(registerDTO.getLicenseNo());
        driver.setAvailable(true);

        driverRepository.save(driver);

        log.info("Driver registered successfully: {}", savedUser.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverDTO> filterDrivers(Long branchId) {
        if (branchId == null) {
            return getAllDrivers();
        }

        return driverRepository.findByBranchBranchId(branchId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DriverDTO selectDriver(long driverId) {
        Driver driver = getDriverById(driverId);
        return toDTO(driver);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverDTO getMyProfile(String username) {
        Driver driver = driverRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Driver profile not found"
                ));

        return toDTO(driver);
    }

    @Override
    public void updateDriver(DriverDTO driverDTO) {
        if (driverDTO.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Driver ID is required"
            );
        }

        Driver driver = getDriverById(driverDTO.getId());

        if (driverDTO.getLicenseNumber() != null
                && !driverDTO.getLicenseNumber().isBlank()) {
            driver.setLicenseNo(driverDTO.getLicenseNumber());
        }

        if (driverDTO.getLicenseExpiry() != null) {
            driver.setLicenseExpiry(driverDTO.getLicenseExpiry());
        }

        if (driverDTO.getCurrentVehiclePlate() != null
                && !driverDTO.getCurrentVehiclePlate().isBlank()) {
            driver.setCurrentVehiclePlate(driverDTO.getCurrentVehiclePlate());
        }
        if (driverDTO.getAvailable() != null) {
            driver.setAvailable(driverDTO.getAvailable());
        }
        driverRepository.save(driver);

        log.info("Driver updated: {}", driver.getDriverId());
    }

    @Override
    public void deleteDriver(long driverId) {
        Driver driver = getDriverById(driverId);
        driverRepository.delete(driver);

        log.info("Driver deleted: {}", driverId);
    }

    private Driver getDriverById(long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Driver not found"
                ));
    }

    private DriverDTO toDTO(Driver driver) {
        User user = driver.getUser();

        return DriverDTO.builder()
                .id(driver.getDriverId())
                .userId(user.getUserId())
                .fullName(user.getUsername())
                .phoneNumber(null)
                .licenseNumber(driver.getLicenseNo())
                .licenseExpiry(driver.getLicenseExpiry())
                .currentVehiclePlate(driver.getCurrentVehiclePlate())
                .branchName(driver.getBranch().getName())
                .available(driver.isAvailable())
                .build();
    }
}