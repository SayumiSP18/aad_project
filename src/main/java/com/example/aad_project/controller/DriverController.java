package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.DriverDTO;
import com.example.aad_project.dto.DriverRegisterDTO;
import com.example.aad_project.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "v1/drivers")
@CrossOrigin
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse register( @RequestBody DriverRegisterDTO registerDTO) {
        driverService.registerDriver(registerDTO);
        return new CommonResponse(0, "Driver registered successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllDrivers() {
        List<DriverDTO> drivers = driverService.getAllDrivers();
        return new CommonResponse(0, drivers, "Get all drivers");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterDrivers(@RequestParam(value = "branchId", required = false) Long branchId) {
        List<DriverDTO> drivers = driverService.filterDrivers(branchId);
        return new CommonResponse(0, drivers, "Filter drivers");
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getMyProfile(Principal principal) {
        DriverDTO dto = driverService.getMyProfile(principal.getName());
        return new CommonResponse(0, dto, "My profile");
    }

    @GetMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectDriver(@PathVariable long driverId) {
        DriverDTO dto = driverService.selectDriver(driverId);
        return new CommonResponse(0, dto, "Driver details");
    }

    @PutMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateDriver(@PathVariable long driverId, @RequestBody DriverDTO driverDTO) {
        driverDTO.setId(driverId);
        driverService.updateDriver(driverDTO);
        return new CommonResponse(0, "Driver updated successfully");
    }

    @DeleteMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteDriver(@PathVariable long driverId) {
        driverService.deleteDriver(driverId);
        return new CommonResponse(0, "Driver deleted");
    }
}
