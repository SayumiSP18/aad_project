package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.dto.DeliveryDTO;
import com.example.aad_project.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/deliveries")
@CrossOrigin
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveDelivery( @RequestBody DeliveryDTO deliveryDTO) {
        deliveryService.saveDelivery(deliveryDTO);
        return new CommonResponse(0, "Delivery assigned successfully");
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries();
        return new CommonResponse(0, deliveries, "Get all deliveries");
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse filterDeliveries(@RequestParam(value = "driverId", required = false) Long driverId) {
        List<DeliveryDTO> deliveries = deliveryService.filterDeliveries(driverId);
        return new CommonResponse(0, deliveries, "Filter deliveries");
    }

    @GetMapping(value = "/{deliveryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse selectDelivery(@PathVariable long deliveryId) {
        DeliveryDTO dto = deliveryService.selectDelivery(deliveryId);
        return new CommonResponse(0, dto, "Delivery details");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateDelivery( @RequestBody DeliveryDTO deliveryDTO) {
        deliveryService.updateDelivery(deliveryDTO);
        return new CommonResponse(0, "Delivery updated");
    }

    @DeleteMapping(value = "/{deliveryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse deleteDelivery(@PathVariable long deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
        return new CommonResponse(0, "Delivery deleted");
    }}
