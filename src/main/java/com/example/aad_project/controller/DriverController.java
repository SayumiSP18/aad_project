package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.DriverDTO;
import com.example.aad_project.dto.DriverRegisterDTO;
import com.example.aad_project.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    }}
