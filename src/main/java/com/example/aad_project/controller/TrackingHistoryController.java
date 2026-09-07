package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.TrackingHistoryDTO;
import com.example.aad_project.service.TrackingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/tracking-history")
@CrossOrigin
@RequiredArgsConstructor
public class TrackingHistoryController {

    private final TrackingHistoryService trackingHistoryService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveHistory( @RequestBody TrackingHistoryDTO historyDTO) {
        trackingHistoryService.saveHistory(historyDTO);
        return new CommonResponse(0, "Tracking update recorded successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllHistory() {
        List<TrackingHistoryDTO> history = trackingHistoryService.getAllHistory();
        return new CommonResponse(0, history, "Get all tracking history");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterHistory(@RequestParam(value = "parcelId", required = false) Long parcelId) {
        List<TrackingHistoryDTO> history = trackingHistoryService.filterHistory(parcelId);
        return new CommonResponse(0, history, "Filter tracking history");
    }

    @GetMapping(value = "/{historyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectHistory(@PathVariable long historyId) {
        TrackingHistoryDTO dto = trackingHistoryService.selectHistory(historyId);
        return new CommonResponse(0, dto, "Tracking record details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateHistory( @RequestBody TrackingHistoryDTO historyDTO) {
        trackingHistoryService.updateHistory(historyDTO);
        return new CommonResponse(0, "Tracking record updated");
    }

    @DeleteMapping(value = "/{historyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteHistory(@PathVariable long historyId) {
        trackingHistoryService.deleteHistory(historyId);
        return new CommonResponse(0, "Tracking record deleted");
    }

}
