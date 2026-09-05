package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private long bookingId;

//    @NotNull(message = "Parcel is required")
    private Long parcelId;

    private String trackingNo;

//    @NotNull(message = "Pickup branch is required")
    private Long pickupBranchId;

    private String pickupBranchName;
    private LocalDateTime bookingDate;

//    @PositiveOrZero(message = "Estimated cost must be zero or greater")
    private double estimatedCost;

    public BookingDTO(long bookingId, long parcelId, String trackingNo, long pickupBranchId, String pickupBranchName,
                      LocalDateTime bookingDate, double estimatedCost) {
        this.bookingId = bookingId;
        this.parcelId = parcelId;
        this.trackingNo = trackingNo;
        this.pickupBranchId = pickupBranchId;
        this.pickupBranchName = pickupBranchName;
        this.bookingDate = bookingDate;
        this.estimatedCost = estimatedCost;
    }}
