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
        Zone zone = zoneRepository.findById(rateDTO.getZoneId())
                .orElseThrow(() -> new CustomException(404, "Zone not found"));

        Rate rate = new Rate();
        rate.setZone(zone);
        rate.setWeightFrom(rateDTO.getWeightFrom());
        rate.setWeightTo(rateDTO.getWeightTo());
        rate.setPricePerKg(rateDTO.getPricePerKg());
        rateRepository.save(rate);
        log.info("New rate created for zone: {}", zone.getZoneName());
    }

    @Override
    public List<RateDTO> getAllRates() {
        return rateRepository.getAllRates();
    }

    @Override
    public List<RateDTO> filterRates(Long zoneId) {
        return rateRepository.filterRates(zoneId);
    }

    @Override
    public RateDTO selectRate(long rateId) {
        return rateRepository.selectRate(rateId)
                .orElseThrow(() -> new CustomException(404, "Rate not found"));
    }

    @Override
    public void updateRate(RateDTO rateDTO) {
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
    }

    @Override
    public void deleteRate(long rateId) {
        if (!rateRepository.existsById(rateId))
            throw new CustomException(404, "Rate not found");
        rateRepository.deleteById(rateId);
    }
}
