package com.example.aad_project.dto.request;

import lombok.Data;

@Data
public class RouteCreateRequestDTO {
//    @NotNull(message = "Origin branch is required")
    private Long originBranchId;

//    @NotNull(message = "Destination branch is required")
    private Long destBranchId;

//    @Positive(message = "Distance must be greater than 0")
    private double distanceKm;
}
