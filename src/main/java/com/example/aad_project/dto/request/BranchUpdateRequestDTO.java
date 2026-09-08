package com.example.aad_project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BranchUpdateRequestDTO {
    @Positive(message = "Branch id is required")
    private long branchId;

    @NotBlank(message = "Branch name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    private Long zoneId;
}
