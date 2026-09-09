package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.RateDTO;
import com.example.aad_project.service.RateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/rates")
@CrossOrigin
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveRate(@Valid @RequestBody RateDTO rateDTO) {
        rateService.saveRate(rateDTO);
        return new CommonResponse(0, "Rate created successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllRates() {
        List<RateDTO> rates = rateService.getAllRates();
        return new CommonResponse(0, rates, "Get all rates");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterRates(@RequestParam(value = "zoneId", required = false) Long zoneId) {
        List<RateDTO> rates = rateService.filterRates(zoneId);
        return new CommonResponse(0, rates, "Filter rates");
    }

    @GetMapping(value = "/{rateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectRate(@PathVariable long rateId) {
        RateDTO dto = rateService.selectRate(rateId);
        return new CommonResponse(0, dto, "Rate details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateRate(@Valid @RequestBody RateDTO rateDTO) {
        rateService.updateRate(rateDTO);
        return new CommonResponse(0, "Rate updated");
    }

    @DeleteMapping(value = "/{rateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteRate(@PathVariable long rateId) {
        rateService.deleteRate(rateId);
        return new CommonResponse(0, "Rate deleted");
    }
}
