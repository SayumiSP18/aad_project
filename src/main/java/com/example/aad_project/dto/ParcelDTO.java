package com.example.aad_project.dto;

import com.example.aad_project.enumaration.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDTO {

    private long parcelId;

//    @NotNull(message = "Customer is required")
    private Long customerId;

    private String customerName;
    private String trackingNo;

//    @Positive(message = "Weight must be greater than 0")
    private double weight;

    private String description;

//    @NotBlank(message = "Receiver name is required")
    private String receiverName;

//    @NotBlank(message = "Receiver address is required")
    private String receiverAddress;

    private ParcelStatus status;

    public ParcelDTO(long parcelId, long customerId, String customerName, String trackingNo, double weight,
                     String description, String receiverName, String receiverAddress, ParcelStatus status) {
        this.parcelId = parcelId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.trackingNo = trackingNo;
        this.weight = weight;
        this.description = description;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.status = status;
    }
}
