package com.example.aad_project.repository;

import com.example.aad_project.dto.response.RouteResponseDTO;
import com.example.aad_project.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.response.RouteResponseDTO(r.routeId, r.originBranch.branchId, r.originBranch.name, " +
            "r.destBranch.branchId, r.destBranch.name, r.distanceKm) FROM Route r")
    List<RouteResponseDTO> getAllRoutes();

    @Query(value = "SELECT new com.example.aad_project.dto.response.RouteResponseDTO(r.routeId, r.originBranch.branchId, r.originBranch.name, " +
            "r.destBranch.branchId, r.destBranch.name, r.distanceKm) FROM Route r WHERE r.routeId = :routeId")
    Optional<RouteResponseDTO> selectRoute(@Param("routeId") long routeId);

    @Query(value = "SELECT new com.example.aad_project.dto.response.RouteResponseDTO(r.routeId, r.originBranch.branchId, r.originBranch.name, " +
            "r.destBranch.branchId, r.destBranch.name, r.distanceKm) FROM Route r " +
            "WHERE (:originBranchId IS NULL OR r.originBranch.branchId = :originBranchId)")
    List<RouteResponseDTO> filterRoutes(@Param("originBranchId") Long originBranchId);}
