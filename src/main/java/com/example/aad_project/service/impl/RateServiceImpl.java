package com.example.aad_project.service.impl;

import com.example.aad_project.dto.RateDTO;
import com.example.aad_project.entity.Rate;
import com.example.aad_project.entity.Zone;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.RateRepository;
import com.example.aad_project.repository.ZoneRepository;
import com.example.aad_project.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final ZoneRepository zoneRepository;

    @Override
    public void saveRate(RateDTO rateDTO) {
        try {
            Zone zone = zoneRepository.findById(rateDTO.getZoneId())
                    .orElseThrow(() -> new CustomException(404, "Zone not found"));

            Rate rate = new Rate();
            rate.setZone(zone);
            rate.setWeightFrom(rateDTO.getWeightFrom());
            rate.setWeightTo(rateDTO.getWeightTo());
            rate.setPricePerKg(rateDTO.getPricePerKg());
            rateRepository.save(rate);
            log.info("New rate created for zone: {}", zone.getZoneName());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save rate for zone {}: {}", rateDTO.getZoneId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to create rate");
        }
    }

    @Override
    public List<RateDTO> getAllRates() {
        try {
            return rateRepository.getAllRates();
        } catch (Exception e) {
            log.error("Failed to fetch rates: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch rates");
        }
    }

    @Override
    public List<RateDTO> filterRates(Long zoneId) {
        try {
            return rateRepository.filterRates(zoneId);
        } catch (Exception e) {
            log.error("Failed to filter rates by zone {}: {}", zoneId, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter rates");
        }
    }

    @Override
    public RateDTO selectRate(long rateId) {
        try {
            return rateRepository.selectRate(rateId)
                    .orElseThrow(() -> new CustomException(404, "Rate not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch rate {}: {}", rateId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch rate");
        }
    }

    @Override
    public void updateRate(RateDTO rateDTO) {
        try {
            Rate rate = rateRepository.findById(rateDTO.getRateId())
                    .orElseThrow(() -> new CustomException(404, "Rate not found"));

            if (rateDTO.getZoneId() != null) {
                Zone zone = zoneRepository.findById(rateDTO.getZoneId())
                        .orElseThrow(() -> new CustomException(404, "Zone not found"));
                rate.setZone(zone);
            }

            rate.setWeightFrom(rateDTO.getWeightFrom());
            rate.setWeightTo(rateDTO.getWeightTo());
            rate.setPricePerKg(rateDTO.getPricePerKg());
            rateRepository.save(rate);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update rate {}: {}", rateDTO.getRateId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update rate");
        }
    }

    @Override
    public void deleteRate(long rateId) {
        try {
            if (!rateRepository.existsById(rateId))
                throw new CustomException(404, "Rate not found");
            rateRepository.deleteById(rateId);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete rate {}: {}", rateId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete rate");
        }
    }
}