package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.ParcelDTO;
import com.example.aad_project.service.ParcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/parcels")
@CrossOrigin
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveParcel(@Valid @RequestBody ParcelDTO parcelDTO) {
        parcelService.saveParcel(parcelDTO);
        return new CommonResponse(0, "Parcel created successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllParcels() {
        List<ParcelDTO> parcels = parcelService.getAllParcels();
        return new CommonResponse(0, parcels, "Get all parcels");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterParcels(@RequestParam(value = "customerId", required = false) Long customerId,
                                        @RequestParam(value = "trackingNo", required = false) String trackingNo) {
        List<ParcelDTO> parcels = parcelService.filterParcels(customerId, trackingNo);
        return new CommonResponse(0, parcels, "Filter parcels");
    }

    @GetMapping(value = "/{parcelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectParcel(@PathVariable long parcelId) {
        ParcelDTO dto = parcelService.selectParcel(parcelId);
        return new CommonResponse(0, dto, "Parcel details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateParcel(@Valid @RequestBody ParcelDTO parcelDTO) {
        parcelService.updateParcel(parcelDTO);
        return new CommonResponse(0, "Parcel updated");
    }

    @DeleteMapping(value = "/{parcelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteParcel(@PathVariable long parcelId) {
        parcelService.deleteParcel(parcelId);
        return new CommonResponse(0, "Parcel deleted");
    }
}
