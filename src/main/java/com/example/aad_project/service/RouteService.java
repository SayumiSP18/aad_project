package com.example.aad_project.service;

import com.example.aad_project.dto.request.RouteCreateRequestDTO;
import com.example.aad_project.dto.request.RouteUpdateRequestDTO;
import com.example.aad_project.dto.response.RouteResponseDTO;

import java.util.List;

public interface RouteService {
    void saveRoute(RouteCreateRequestDTO request);

    List<RouteResponseDTO> getAllRoutes();

    List<RouteResponseDTO> filterRoutes(Long originBranchId);

    RouteResponseDTO selectRoute(long routeId);

    void updateRoute(RouteUpdateRequestDTO request);

    void deleteRoute(long routeId);
}
