package com.example.aad_project.service;

import com.example.aad_project.dto.DeliveryDTO;

import java.util.List;

public interface DeliveryService {
    void saveDelivery(DeliveryDTO deliveryDTO);

    List<DeliveryDTO> getAllDeliveries();

    List<DeliveryDTO> filterDeliveries(Long driverId);

    DeliveryDTO selectDelivery(long deliveryId);

    void updateDelivery(DeliveryDTO deliveryDTO);

    void deleteDelivery(long deliveryId);
}
