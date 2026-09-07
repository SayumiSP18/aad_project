package com.example.aad_project.service;

import com.example.aad_project.dto.TrackingHistoryDTO;

import java.util.List;

public interface TrackingHistoryService {
    void saveHistory(TrackingHistoryDTO historyDTO);

    List<TrackingHistoryDTO> getAllHistory();

    List<TrackingHistoryDTO> filterHistory(Long parcelId);

    TrackingHistoryDTO selectHistory(long historyId);

    void updateHistory(TrackingHistoryDTO historyDTO);

    void deleteHistory(long historyId);
}
