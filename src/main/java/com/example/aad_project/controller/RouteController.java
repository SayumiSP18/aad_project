package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.request.RouteCreateRequestDTO;
import com.example.aad_project.dto.request.RouteUpdateRequestDTO;
import com.example.aad_project.dto.response.RouteResponseDTO;
import com.example.aad_project.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/routes")
@CrossOrigin
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveRoute(@Valid @RequestBody RouteCreateRequestDTO request) {
        routeService.saveRoute(request);
        return new CommonResponse(0, "Route created successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllRoutes() {
        List<RouteResponseDTO> routes = routeService.getAllRoutes();
        return new CommonResponse(0, routes, "Get all routes");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterRoutes(@RequestParam(value = "originBranchId", required = false) Long originBranchId) {
        List<RouteResponseDTO> routes = routeService.filterRoutes(originBranchId);
        return new CommonResponse(0, routes, "Filter routes");
    }

    @GetMapping(value = "/{routeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectRoute(@PathVariable long routeId) {
        RouteResponseDTO response = routeService.selectRoute(routeId);
        return new CommonResponse(0, response, "Route details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateRoute(@Valid @RequestBody RouteUpdateRequestDTO request) {
        routeService.updateRoute(request);
        return new CommonResponse(0, "Route updated");
    }

    @DeleteMapping(value = "/{routeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteRoute(@PathVariable long routeId) {
        routeService.deleteRoute(routeId);
        return new CommonResponse(0, "Route deleted");
    }
}
