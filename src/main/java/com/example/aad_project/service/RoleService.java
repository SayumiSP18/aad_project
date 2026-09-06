package com.example.aad_project.service;



import com.example.aad_project.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    void saveRole(RoleDTO roleDTO);

    List<RoleDTO> getAllRoles();

    RoleDTO selectRole(long roleId);

    void deleteRole(long roleId);
}
