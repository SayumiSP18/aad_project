package com.example.aad_project.service.impl;

import com.example.aad_project.dto.RoleDTO;
import com.example.aad_project.entity.Role;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.RoleRepository;
import com.example.aad_project.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void saveRole(RoleDTO roleDTO) {
        if (roleRepository.findByRoleName(roleDTO.getRoleName()).isPresent())
            throw new CustomException(409, "Role already exists");

        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        roleRepository.save(role);
        log.info("New role created: {}", role.getRoleName());
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDTO(role.getRoleId(), role.getRoleName()))
                .toList();
    }

    @Override
    public RoleDTO selectRole(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException(404, "Role not found"));
        return new RoleDTO(role.getRoleId(), role.getRoleName());
    }

    @Override
    public void deleteRole(long roleId) {
        if (!roleRepository.existsById(roleId))
            throw new CustomException(404, "Role not found");
        roleRepository.deleteById(roleId);
    }
}
