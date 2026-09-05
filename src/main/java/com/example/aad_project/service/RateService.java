package com.example.aad_project.service;

import com.example.aad_project.dto.RateDTO;

import java.util.List;

public interface RateService {

    void saveRate(RateDTO rateDTO);

    List<RateDTO> getAllRates();

    List<RateDTO> filterRates(Long zoneId);

    RateDTO selectRate(long rateId);

    void updateRate(RateDTO rateDTO);

    void deleteRate(long rateId);
}
