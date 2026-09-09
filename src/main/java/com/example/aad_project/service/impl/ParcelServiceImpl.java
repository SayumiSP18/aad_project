package com.example.aad_project.service.impl;

import com.example.aad_project.dto.ParcelDTO;
import com.example.aad_project.entity.Customer;
import com.example.aad_project.entity.Parcel;
import com.example.aad_project.enumaration.ParcelStatus;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.CustomerRepository;
import com.example.aad_project.repository.ParcelRepository;
import com.example.aad_project.service.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void saveParcel(ParcelDTO parcelDTO) {
        Customer customer = customerRepository.findById(parcelDTO.getCustomerId())
                .orElseThrow(() -> new CustomException(404, "Customer not found"));

        Parcel parcel = new Parcel();
        parcel.setCustomer(customer);
        parcel.setTrackingNo("PCL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        parcel.setWeight(parcelDTO.getWeight());
        parcel.setDescription(parcelDTO.getDescription());
        parcel.setReceiverName(parcelDTO.getReceiverName());
        parcel.setReceiverAddress(parcelDTO.getReceiverAddress());
        parcel.setStatus(ParcelStatus.BOOKED);
        parcelRepository.save(parcel);
        log.info("New parcel created with tracking no: {}", parcel.getTrackingNo());
    }

    @Override
    public List<ParcelDTO> getAllParcels() {
        return parcelRepository.getAllParcels();
    }

    @Override
    public List<ParcelDTO> filterParcels(Long customerId, String trackingNo) {
        return parcelRepository.filterParcels(customerId, trackingNo);
    }

    @Override
    public ParcelDTO selectParcel(long parcelId) {
        return parcelRepository.selectParcel(parcelId)
                .orElseThrow(() -> new CustomException(404, "Parcel not found"));
    }

    @Override
    public void updateParcel(ParcelDTO parcelDTO) {
        Parcel parcel = parcelRepository.findById(parcelDTO.getParcelId())
                .orElseThrow(() -> new CustomException(404, "Parcel not found"));

        parcel.setWeight(parcelDTO.getWeight());
        parcel.setDescription(parcelDTO.getDescription());
        parcel.setReceiverName(parcelDTO.getReceiverName());
        parcel.setReceiverAddress(parcelDTO.getReceiverAddress());

        if (parcelDTO.getStatus() != null)
            parcel.setStatus(parcelDTO.getStatus());

        parcelRepository.save(parcel);
    }

    @Override
    public void deleteParcel(long parcelId) {
        if (!parcelRepository.existsById(parcelId))
            throw new CustomException(404, "Parcel not found");
        parcelRepository.deleteById(parcelId);
    }
}
