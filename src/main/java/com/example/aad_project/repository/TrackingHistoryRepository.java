package com.example.aad_project.repository;

import com.example.aad_project.dto.TrackingHistoryDTO;
import com.example.aad_project.entity.TrackingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingHistoryRepository extends JpaRepository<TrackingHistory, Long> {

    @Query(value = "SELECT new com.example.aad_project.dto.TrackingHistoryDTO(t.historyId, t.parcel.parcelId, t.parcel.trackingNo, " +
            "t.status, t.location, t.updatedAt) FROM TrackingHistory t")
    List<TrackingHistoryDTO> getAllHistory();

    @Query(value = "SELECT new com.example.aad_project.dto.TrackingHistoryDTO(t.historyId, t.parcel.parcelId, t.parcel.trackingNo, " +
            "t.status, t.location, t.updatedAt) FROM TrackingHistory t WHERE t.historyId = :historyId")
    Optional<TrackingHistoryDTO> selectHistory(@Param("historyId") long historyId);

    @Query(value = "SELECT new com.example.aad_project.dto.TrackingHistoryDTO(t.historyId, t.parcel.parcelId, t.parcel.trackingNo, " +
            "t.status, t.location, t.updatedAt) FROM TrackingHistory t " +
            "WHERE (:parcelId IS NULL OR t.parcel.parcelId = :parcelId) ORDER BY t.updatedAt DESC")
    List<TrackingHistoryDTO> filterHistory(@Param("parcelId") Long parcelId);
}
