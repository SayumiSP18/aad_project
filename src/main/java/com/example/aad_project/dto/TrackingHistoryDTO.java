package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingHistoryDTO {

    private long historyId;

//    @NotNull(message = "Parcel is required")
    private Long parcelId;

    private String trackingNo;

//    @NotBlank(message = "Status is required")
    private String status;

    private String location;

    private LocalDateTime updatedAt;

    public TrackingHistoryDTO(long historyId, long parcelId, String trackingNo, String status,
                              String location, LocalDateTime updatedAt) {
        this.historyId = historyId;
        this.parcelId = parcelId;
        this.trackingNo = trackingNo;
        this.status = status;
        this.location = location;
        this.updatedAt = updatedAt;
    }
}
