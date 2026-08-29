package com.example.aad_project.dto.request;

import lombok.Data;

@Data
public class ZoneUpdateRequestDTO {

//    @Positive(message = "Zone id is required")
    private long zoneId;

//    @NotBlank(message = "Zone name is required")
    private String zoneName;
}
