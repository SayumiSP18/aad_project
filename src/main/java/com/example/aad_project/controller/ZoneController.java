package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.request.ZoneCreateRequestDTO;
import com.example.aad_project.dto.request.ZoneUpdateRequestDTO;
import com.example.aad_project.dto.response.ZoneResponseDTO;
import com.example.aad_project.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/zones")
@CrossOrigin
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveZone( @RequestBody ZoneCreateRequestDTO request) {
        zoneService.saveZone(request);
        return new CommonResponse(0, "Zone created successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllZones() {
        List<ZoneResponseDTO> zones = zoneService.getAllZones();
        return new CommonResponse(0, zones, "Get all zones");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterZones(@RequestParam(value = "zoneName", required = false) String zoneName) {
        List<ZoneResponseDTO> zones = zoneService.filterZones(zoneName);
        return new CommonResponse(0, zones, "Filter zones");
    }

    @GetMapping(value = "/{zoneId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectZone(@PathVariable long zoneId) {
        ZoneResponseDTO response = zoneService.selectZone(zoneId);
        return new CommonResponse(0, response, "Zone details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateZone( @RequestBody ZoneUpdateRequestDTO request) {
        zoneService.updateZone(request);
        return new CommonResponse(0, "Zone updated");
    }

    @DeleteMapping(value = "/{zoneId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteZone(@PathVariable long zoneId) {
        zoneService.deleteZone(zoneId);
        return new CommonResponse(0, "Zone deleted");
    }}
