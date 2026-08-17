package com.example.aad_project.entity;

import com.example.aad_project.enumaration.ParcelStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parcelId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(unique = true, nullable = false)
    private String trackingNo;

    private double weight;
    private String description;
    private String receiverName;
    private String receiverAddress;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;
}
