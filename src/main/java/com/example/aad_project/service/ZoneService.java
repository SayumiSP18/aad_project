package com.example.aad_project.service;

import com.example.aad_project.dto.request.ZoneCreateRequestDTO;
import com.example.aad_project.dto.request.ZoneUpdateRequestDTO;
import com.example.aad_project.dto.response.ZoneResponseDTO;

import java.util.List;

public interface ZoneService {

    void saveZone(ZoneCreateRequestDTO request);

    List<ZoneResponseDTO> getAllZones();

    List<ZoneResponseDTO> filterZones(String zoneName);

    ZoneResponseDTO selectZone(long zoneId);

    void updateZone(ZoneUpdateRequestDTO request);

    void deleteZone(long zoneId);
}
