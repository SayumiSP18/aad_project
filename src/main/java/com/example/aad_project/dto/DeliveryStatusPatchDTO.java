package com.example.aad_project.dto;

import com.example.aad_project.enumaration.ParcelStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusPatchDTO {

    @NotNull(message = "Status is required")
    private ParcelStatus status;
}

