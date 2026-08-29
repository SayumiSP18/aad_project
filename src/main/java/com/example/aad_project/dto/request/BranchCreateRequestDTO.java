package com.example.aad_project.dto.request;

import lombok.Data;

@Data
public class BranchCreateRequestDTO {
//    @NotBlank(message = "Branch name is required")
    private String name;

//    @NotBlank(message = "Address is required")
    private String address;

//    @NotNull(message = "Zone is required")
    private Long zoneId;
}
