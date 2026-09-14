package com.example.aad_project.service.impl;

import com.example.aad_project.dto.request.ZoneCreateRequestDTO;
import com.example.aad_project.dto.request.ZoneUpdateRequestDTO;
import com.example.aad_project.dto.response.ZoneResponseDTO;
import com.example.aad_project.entity.Zone;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.ZoneRepository;
import com.example.aad_project.service.ZoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    @Override
    public void saveZone(ZoneCreateRequestDTO request) {
        try {
            Zone zone = new Zone();
            zone.setZoneName(request.getZoneName());
            zoneRepository.save(zone);
            log.info("New zone created: {}", zone.getZoneName());
        } catch (Exception e) {
            log.error("Failed to save zone '{}': {}", request.getZoneName(), e.getMessage(), e);
            throw new CustomException(500, "Failed to create zone");
        }
    }

    @Override
    public List<ZoneResponseDTO> getAllZones() {
        try {
            return zoneRepository.getAllZones();
        } catch (Exception e) {
            log.error("Failed to fetch zones: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch zones");
        }
    }

    @Override
    public List<ZoneResponseDTO> filterZones(String zoneName) {
        try {
            return zoneRepository.filterZones(zoneName);
        } catch (Exception e) {
            log.error("Failed to filter zones by name '{}': {}", zoneName, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter zones");
        }
    }

    @Override
    public ZoneResponseDTO selectZone(long zoneId) {
        try {
            return zoneRepository.selectZone(zoneId)
                    .orElseThrow(() -> new CustomException(404, "Zone not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch zone {}: {}", zoneId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch zone");
        }
    }

    @Override
    public void updateZone(ZoneUpdateRequestDTO request) {
        try {
            Zone zone = zoneRepository.findById(request.getZoneId())
                    .orElseThrow(() -> new CustomException(404, "Zone not found"));

            zone.setZoneName(request.getZoneName());
            zoneRepository.save(zone);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update zone {}: {}", request.getZoneId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update zone");
        }
    }

    @Override
    public void deleteZone(long zoneId) {
        try {
            if (!zoneRepository.existsById(zoneId))
                throw new CustomException(404, "Zone not found");
            zoneRepository.deleteById(zoneId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete zone {}: {}", zoneId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete zone");
        }
    }
}