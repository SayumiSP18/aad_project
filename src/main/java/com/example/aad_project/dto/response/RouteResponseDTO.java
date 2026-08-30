package com.example.aad_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponseDTO {
    private long routeId;
    private long originBranchId;
    private String originBranchName;
    private long destBranchId;
    private String destBranchName;
    private double distanceKm;
}
