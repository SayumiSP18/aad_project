package com.example.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long userId;
    private String username;
    private String userRoles;
    private String password;

    public UserDTO(String username, String userRoles, String password) {
        this.username = username;
        this.userRoles = userRoles;
        this.password = password;
    }

    public UserDTO(long userId, String username, String userRoles) {
        this.userId = userId;
        this.username = username;
        this.userRoles = userRoles;
    }
}
