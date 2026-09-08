package com.example.aad_project.dto;

import com.example.aad_project.enumaration.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {

    private long deliveryId;

    @NotNull(message = "Parcel is required")
    private Long parcelId;

    private String trackingNo;

    @NotNull(message = "Driver is required")
    private Long driverId;

    private String driverUsername;

    @NotNull(message = "Vehicle is required")
    private Long vehicleId;

    private String vehicleNo;

    private Long routeId;

    private ParcelStatus status;

    private LocalDateTime deliveredAt;

    public DeliveryDTO(long deliveryId, long parcelId, String trackingNo, long driverId, String driverUsername,
                       long vehicleId, String vehicleNo, Long routeId, ParcelStatus status, LocalDateTime deliveredAt) {
        this.deliveryId = deliveryId;
        this.parcelId = parcelId;
        this.trackingNo = trackingNo;
        this.driverId = driverId;
        this.driverUsername = driverUsername;
        this.vehicleId = vehicleId;
        this.vehicleNo = vehicleNo;
        this.routeId = routeId;
        this.status = status;
        this.deliveredAt = deliveredAt;
    }
}
