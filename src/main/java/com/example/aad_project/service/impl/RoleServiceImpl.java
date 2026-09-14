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
        try {
            if (roleRepository.findByRoleName(roleDTO.getRoleName()).isPresent())
                throw new CustomException(409, "Role already exists");

            Role role = new Role();
            role.setRoleName(roleDTO.getRoleName());
            roleRepository.save(role);
            log.info("New role created: {}", role.getRoleName());

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error saving role: {}", roleDTO.getRoleName(), e);
            throw new CustomException(500, "Failed to save role");
        }
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        try {
            return roleRepository.findAll().stream()
                    .map(role -> new RoleDTO(role.getRoleId(), role.getRoleName()))
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching all roles", e);
            throw new CustomException(500, "Failed to fetch roles");
        }
    }

    @Override
    public RoleDTO selectRole(long roleId) {
        try {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new CustomException(404, "Role not found"));
            return new RoleDTO(role.getRoleId(), role.getRoleName());

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error selecting role with id: {}", roleId, e);
            throw new CustomException(500, "Failed to fetch role");
        }
    }

    @Override
    public void deleteRole(long roleId) {
        try {
            if (!roleRepository.existsById(roleId))
                throw new CustomException(404, "Role not found");
            roleRepository.deleteById(roleId);
            log.info("Role deleted with id: {}", roleId);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting role with id: {}", roleId, e);
            throw new CustomException(500, "Failed to delete role");
        }
    }
}