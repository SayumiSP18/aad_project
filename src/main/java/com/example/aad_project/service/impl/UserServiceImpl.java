package com.example.aad_project.service.impl;

import com.example.aad_project.dto.AuthDTO;
import com.example.aad_project.dto.UserDTO;
import com.example.aad_project.entity.Role;
import com.example.aad_project.entity.User;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.RoleRepository;
import com.example.aad_project.repository.UserRepository;
import com.example.aad_project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
//    @Transactional(readOnly = true)
    public UserDTO authenticate(AuthDTO authDTO) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(authDTO.getUserName());
            if (optionalUser.isEmpty())
                throw new CustomException(404, "User not found");

            User user = optionalUser.get();

            if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
                throw new CustomException(401, "Invalid username or password");
            }
            log.info("User logged in: {}", user.getUsername());
            return new UserDTO(user.getUserId(), user.getUsername(), user.getUserRoles().getRoleName(), null);
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to authenticate user '{}': {}", authDTO.getUserName(), e.getMessage(), e);
            throw new CustomException(500, "Authentication failed");
        }
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        try {
            if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
                throw new CustomException(409, "Username already taken");

            Role role = roleRepository.findByRoleName(userDTO.getUserRoles())
                    .orElseThrow(() -> new CustomException(404, "Role not found: " + userDTO.getUserRoles()));

            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setUserRoles(role);

            userRepository.save(user);
            log.info("New user registered: {}", user.getUsername());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to save user '{}': {}", userDTO.getUsername(), e.getMessage(), e);
            throw new CustomException(500, "Failed to create user");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            return userRepository.getAllUsers();
        } catch (Exception e) {
            log.error("Failed to fetch users: {}", e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch users");
        }
    }

    @Override
    public UserDTO getUserDetails(String username, String password) {
        return null;
    }

    @Override
    public List<UserDTO> filterUsers(String username) {
        try {
            return userRepository.filterUser(username);
        } catch (Exception e) {
            log.error("Failed to filter users by username '{}': {}", username, e.getMessage(), e);
            throw new CustomException(500, "Failed to filter users");
        }
    }

    @Override
    public UserDTO selectUser(long userId) {
        try {
            return userRepository.selectUser(userId)
                    .orElseThrow(() -> new CustomException(404, "User not found"));
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to fetch user {}: {}", userId, e.getMessage(), e);
            throw new CustomException(500, "Failed to fetch user");
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        try {
            User user = userRepository.findById(userDTO.getUserId())
                    .orElseThrow(() -> new CustomException(404, "User not found"));

            // Check for a duplicate username BEFORE reassigning, otherwise this check is dead code
            if (!user.getUsername().equals(userDTO.getUsername())
                    && userRepository.existsByUsername(userDTO.getUsername())) {
                throw new CustomException(409, "Username already taken");
            }

            user.setUsername(userDTO.getUsername());

            if (userDTO.getUserRoles() != null && !userDTO.getUserRoles().isBlank()) {
                Role role = roleRepository.findByRoleName(userDTO.getUserRoles())
                        .orElseThrow(() ->
                                new CustomException(404,
                                        "Role not found: " + userDTO.getUserRoles()));
                user.setUserRoles(role);
            }

            if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank())
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(user);
            log.info("User updated: {}", user.getUsername());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to update user {}: {}", userDTO.getUserId(), e.getMessage(), e);
            throw new CustomException(500, "Failed to update user");
        }
    }

    @Override
    public void deleteUser(long userId) {
        try {
            if (!userRepository.existsById(userId))
                throw new CustomException(404, "User not found");
            userRepository.deleteById(userId);
//        log.info("User deleted: {}", user.getUsername());
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error("Failed to delete user {}: {}", userId, e.getMessage(), e);
            throw new CustomException(500, "Failed to delete user");
        }
    }
}