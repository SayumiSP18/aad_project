package com.example.aad_project.service.impl;

import com.example.aad_project.dto.TrackingHistoryDTO;
import com.example.aad_project.entity.Parcel;
import com.example.aad_project.entity.TrackingHistory;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.ParcelRepository;
import com.example.aad_project.repository.TrackingHistoryRepository;
import com.example.aad_project.service.TrackingHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingHistoryServiceImpl implements TrackingHistoryService {

    private final TrackingHistoryRepository trackingHistoryRepository;
    private final ParcelRepository parcelRepository;

    @Override
    public void saveHistory(TrackingHistoryDTO historyDTO) {
        try {
            Parcel parcel = parcelRepository.findById(historyDTO.getParcelId())
                    .orElseThrow(() -> new CustomException(404, "Parcel not found"));

            TrackingHistory history = new TrackingHistory();
            history.setParcel(parcel);
            history.setStatus(historyDTO.getStatus());
            history.setLocation(historyDTO.getLocation());
            history.setUpdatedAt(LocalDateTime.now());
            trackingHistoryRepository.save(history);
            log.info("New tracking update for parcel {}: {}", parcel.getTrackingNo(), historyDTO.getStatus());

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error saving tracking history for parcel id: {}", historyDTO.getParcelId(), e);
            throw new CustomException(500, "Failed to save tracking history");
        }
    }

    @Override
    public List<TrackingHistoryDTO> getAllHistory() {
        try {
            return trackingHistoryRepository.getAllHistory();
        } catch (Exception e) {
            log.error("Error fetching all tracking history", e);
            throw new CustomException(500, "Failed to fetch tracking history");
        }
    }

    @Override
    public List<TrackingHistoryDTO> filterHistory(Long parcelId) {
        try {
            return trackingHistoryRepository.filterHistory(parcelId);
        } catch (Exception e) {
            log.error("Error filtering tracking history for parcel id: {}", parcelId, e);
            throw new CustomException(500, "Failed to filter tracking history");
        }
    }

    @Override
    public TrackingHistoryDTO selectHistory(long historyId) {
        try {
            return trackingHistoryRepository.selectHistory(historyId)
                    .orElseThrow(() -> new CustomException(404, "Tracking record not found"));
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error selecting tracking history with id: {}", historyId, e);
            throw new CustomException(500, "Failed to fetch tracking history");
        }
    }

    @Override
    public void updateHistory(TrackingHistoryDTO historyDTO) {
        try {
            TrackingHistory history = trackingHistoryRepository.findById(historyDTO.getHistoryId())
                    .orElseThrow(() -> new CustomException(404, "Tracking record not found"));

            history.setStatus(historyDTO.getStatus());
            history.setLocation(historyDTO.getLocation());
            trackingHistoryRepository.save(history);
            log.info("Tracking history updated with id: {}", historyDTO.getHistoryId());

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updating tracking history with id: {}", historyDTO.getHistoryId(), e);
            throw new CustomException(500, "Failed to update tracking history");
        }
    }

    @Override
    public void deleteHistory(long historyId) {
        try {
            if (!trackingHistoryRepository.existsById(historyId))
                throw new CustomException(404, "Tracking record not found");
            trackingHistoryRepository.deleteById(historyId);
            log.info("Tracking history deleted with id: {}", historyId);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting tracking history with id: {}", historyId, e);
            throw new CustomException(500, "Failed to delete tracking history");
        }
    }
}