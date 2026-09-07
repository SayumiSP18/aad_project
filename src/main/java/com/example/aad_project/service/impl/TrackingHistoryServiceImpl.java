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
        Parcel parcel = parcelRepository.findById(historyDTO.getParcelId())
                .orElseThrow(() -> new CustomException(404, "Parcel not found"));

        TrackingHistory history = new TrackingHistory();
        history.setParcel(parcel);
        history.setStatus(historyDTO.getStatus());
        history.setLocation(historyDTO.getLocation());
        history.setUpdatedAt(LocalDateTime.now());
        trackingHistoryRepository.save(history);
        log.info("New tracking update for parcel {}: {}", parcel.getTrackingNo(), historyDTO.getStatus());
    }

    @Override
    public List<TrackingHistoryDTO> getAllHistory() {
        return trackingHistoryRepository.getAllHistory();
    }

    @Override
    public List<TrackingHistoryDTO> filterHistory(Long parcelId) {
        return trackingHistoryRepository.filterHistory(parcelId);
    }

    @Override
    public TrackingHistoryDTO selectHistory(long historyId) {
        return trackingHistoryRepository.selectHistory(historyId)
                .orElseThrow(() -> new CustomException(404, "Tracking record not found"));
    }

    @Override
    public void updateHistory(TrackingHistoryDTO historyDTO) {
        TrackingHistory history = trackingHistoryRepository.findById(historyDTO.getHistoryId())
                .orElseThrow(() -> new CustomException(404, "Tracking record not found"));

        history.setStatus(historyDTO.getStatus());
        history.setLocation(historyDTO.getLocation());
        trackingHistoryRepository.save(history);
    }

    @Override
    public void deleteHistory(long historyId) {
        if (!trackingHistoryRepository.existsById(historyId))
            throw new CustomException(404, "Tracking record not found");
        trackingHistoryRepository.deleteById(historyId);
    }}
