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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

//    @Column(unique = true, nullable = false)
    private String username;

//    @Column(nullable = false)
    private String password;

//    private String email;
//    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role userRoles;

//    private LocalDateTime createdAt;
}
