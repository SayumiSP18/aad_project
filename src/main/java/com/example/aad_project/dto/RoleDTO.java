package com.example.aad_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private long roleId;

    @NotBlank(message = "Role name is required")
    private String roleName;}
