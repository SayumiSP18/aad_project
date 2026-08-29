package com.example.aad_project.dto.request;

import lombok.Data;

@Data
public class ZoneUpdateDTO {

//    @Positive(message = "Zone id is required")
    private long zoneId;

//    @NotBlank(message = "Zone name is required")
    private String zoneName;
}
