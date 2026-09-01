package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.VehicleDTO;
import com.example.aad_project.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/vehicles")
@CrossOrigin
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveVehicle( @RequestBody VehicleDTO vehicleDTO) {
        vehicleService.saveVehicle(vehicleDTO);
        return new CommonResponse(0, "Vehicle registered successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        return new CommonResponse(0, vehicles, "Get all vehicles");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterVehicles(@RequestParam(value = "driverId", required = false) Long driverId) {
        List<VehicleDTO> vehicles = vehicleService.filterVehicles(driverId);
        return new CommonResponse(0, vehicles, "Filter vehicles");
    }

    @GetMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectVehicle(@PathVariable long vehicleId) {
        VehicleDTO dto = vehicleService.selectVehicle(vehicleId);
        return new CommonResponse(0, dto, "Vehicle details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateVehicle( @RequestBody VehicleDTO vehicleDTO) {
        vehicleService.updateVehicle(vehicleDTO);
        return new CommonResponse(0, "Vehicle updated");
    }

    @DeleteMapping(value = "/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteVehicle(@PathVariable long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return new CommonResponse(0, "Vehicle deleted");
    }}
