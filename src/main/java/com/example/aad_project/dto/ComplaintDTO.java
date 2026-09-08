package com.example.aad_project.dto;

import com.example.aad_project.enumaration.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDTO {

    private long complaintId;

    @NotNull(message = "Customer is required")
    private Long customerId;

    private String customerName;

    @NotNull(message = "Parcel is required")
    private Long parcelId;

    private String trackingNo;

    @NotBlank(message = "Description is required")
    private String description;

    private ComplaintStatus status;
    private LocalDateTime createdAt;

    public ComplaintDTO(long complaintId, long customerId, String customerName, long parcelId, String trackingNo,
                        String description, ComplaintStatus status, LocalDateTime createdAt) {
        this.complaintId = complaintId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.parcelId = parcelId;
        this.trackingNo = trackingNo;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }
}
