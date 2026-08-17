package com.example.aad_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    @OneToOne
    @JoinColumn(name = "parcel_id", nullable = false, unique = true)
    private Parcel parcel;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch pickupBranch;

    private LocalDateTime bookingDate;
    private double estimatedCost;
}
