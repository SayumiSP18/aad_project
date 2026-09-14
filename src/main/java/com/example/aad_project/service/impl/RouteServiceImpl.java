package com.example.aad_project.service.impl;

import com.example.aad_project.dto.request.RouteCreateRequestDTO;
import com.example.aad_project.dto.request.RouteUpdateRequestDTO;
import com.example.aad_project.dto.response.RouteResponseDTO;
import com.example.aad_project.entity.Branch;
import com.example.aad_project.entity.Route;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.BranchRepository;
import com.example.aad_project.repository.RouteRepository;
import com.example.aad_project.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final BranchRepository branchRepository;

    @Override
    public void saveRoute(RouteCreateRequestDTO request) {
        try {
            Branch origin = branchRepository.findById(request.getOriginBranchId())
                    .orElseThrow(() -> new CustomException(404, "Origin branch not found"));
            Branch dest = branchRepository.findById(request.getDestBranchId())
                    .orElseThrow(() -> new CustomException(404, "Destination branch not found"));

            Route route = new Route();
            route.setOriginBranch(origin);
            route.setDestBranch(dest);
            route.setDistanceKm(request.getDistanceKm());
            routeRepository.save(route);
            log.info("New route created between {} and {}", origin.getName(), dest.getName());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save route (origin={}, dest={}): {}",
                    request.getOriginBranchId(), request.getDestBranchId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to create route");
        }
    }

    @Override
    public List<RouteResponseDTO> getAllRoutes() {
        try {
            return routeRepository.getAllRoutes();
        } catch (Exception e) {
            log.error("Failed to fetch routes: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch routes");
        }
    }

    @Override
    public List<RouteResponseDTO> filterRoutes(Long originBranchId) {
        try {
            return routeRepository.filterRoutes(originBranchId);
        } catch (Exception e) {
            log.error("Failed to filter routes by origin branch {}: {}", originBranchId, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter routes");
        }
    }

    @Override
    public RouteResponseDTO selectRoute(long routeId) {
        try {
            return routeRepository.selectRoute(routeId)
                    .orElseThrow(() -> new CustomException(404, "Route not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch route {}: {}", routeId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch route");
        }
    }

    @Override
    public void updateRoute(RouteUpdateRequestDTO request) {
        try {
            Route route = routeRepository.findById(request.getRouteId())
                    .orElseThrow(() -> new CustomException(404, "Route not found"));

            if (request.getOriginBranchId() != null) {
                Branch origin = branchRepository.findById(request.getOriginBranchId())
                        .orElseThrow(() -> new CustomException(404, "Origin branch not found"));
                route.setOriginBranch(origin);
            }
            if (request.getDestBranchId() != null) {
                Branch dest = branchRepository.findById(request.getDestBranchId())
                        .orElseThrow(() -> new CustomException(404, "Destination branch not found"));
                route.setDestBranch(dest);
            }

            route.setDistanceKm(request.getDistanceKm());
            routeRepository.save(route);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update route {}: {}", request.getRouteId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update route");
        }
    }

    @Override
    public void deleteRoute(long routeId) {
        try {
            if (!routeRepository.existsById(routeId))
                throw new CustomException(404, "Route not found");
            routeRepository.deleteById(routeId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete route {}: {}", routeId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete route");
        }
    }
}