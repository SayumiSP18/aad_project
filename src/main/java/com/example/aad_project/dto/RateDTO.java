package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {
    private long rateId;

//    @NotNull(message = "Zone is required")
    private Long zoneId;

    private String zoneName;

//    @PositiveOrZero(message = "Weight from must be zero or greater")
    private double weightFrom;

//    @PositiveOrZero(message = "Weight to must be zero or greater")
    private double weightTo;

//    @PositiveOrZero(message = "Price per kg must be zero or greater")
    private double pricePerKg;

    public RateDTO(long rateId, long zoneId, String zoneName, double weightFrom, double weightTo, double pricePerKg) {
        this.rateId = rateId;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.weightFrom = weightFrom;
        this.weightTo = weightTo;
        this.pricePerKg = pricePerKg;
    }
}
