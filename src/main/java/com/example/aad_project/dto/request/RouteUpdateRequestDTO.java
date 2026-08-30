package com.example.aad_project.dto.request;

import lombok.Data;

@Data
public class RouteUpdateRequestDTO {
//    @Positive(message = "Route id is required")
    private long routeId;

    private Long originBranchId;

    private Long destBranchId;

//    @Positive(message = "Distance must be greater than 0")
    private double distanceKm;
}
