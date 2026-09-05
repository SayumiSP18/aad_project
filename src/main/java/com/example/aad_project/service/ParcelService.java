package com.example.aad_project.service;

import com.example.aad_project.dto.ParcelDTO;

import java.util.List;

public interface ParcelService {
    void saveParcel(ParcelDTO parcelDTO);

    List<ParcelDTO> getAllParcels();

    List<ParcelDTO> filterParcels(Long customerId, String trackingNo);

    ParcelDTO selectParcel(long parcelId);

    void updateParcel(ParcelDTO parcelDTO);

    void deleteParcel(long parcelId);
}
