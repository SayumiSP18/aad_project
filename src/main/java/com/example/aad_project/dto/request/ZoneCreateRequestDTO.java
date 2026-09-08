package com.example.aad_project.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ZoneCreateRequestDTO {
    @NotBlank(message = "Zone name is required")
    private String zoneName;
}
