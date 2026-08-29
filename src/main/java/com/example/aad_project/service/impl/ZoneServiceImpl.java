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
        Zone zone = new Zone();
        zone.setZoneName(request.getZoneName());
        zoneRepository.save(zone);
        log.info("New zone created: {}", zone.getZoneName());
    }

    @Override
    public List<ZoneResponseDTO> getAllZones() {
        return zoneRepository.getAllZones();
    }

    @Override
    public List<ZoneResponseDTO> filterZones(String zoneName) {
        return zoneRepository.filterZones(zoneName);
    }

    @Override
    public ZoneResponseDTO selectZone(long zoneId) {
        return zoneRepository.selectZone(zoneId)
                .orElseThrow(() -> new CustomException(404, "Zone not found"));
    }

    @Override
    public void updateZone(ZoneUpdateRequestDTO request) {
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new CustomException(404, "Zone not found"));

        zone.setZoneName(request.getZoneName());
        zoneRepository.save(zone);
    }

    @Override
    public void deleteZone(long zoneId) {
        if (!zoneRepository.existsById(zoneId))
            throw new CustomException(404, "Zone not found");
        zoneRepository.deleteById(zoneId);
    }
}
