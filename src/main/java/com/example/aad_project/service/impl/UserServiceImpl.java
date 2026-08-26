package com.example.aad_project.service.impl;

import com.example.aad_project.dto.AuthDTO;
import com.example.aad_project.dto.UserDTO;
import com.example.aad_project.entity.Role;
import com.example.aad_project.entity.User;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.RoleRepository;
import com.example.aad_project.repository.UserRepository;
import com.example.aad_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO authenticate(AuthDTO authDTO) {

        Optional<User> optionalUser = userRepository.findByUsername(authDTO.getUserName());
        if (optionalUser.isEmpty())
            throw new CustomException(404, "User not found");

        User user = optionalUser.get();

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword()))
            throw new CustomException(401, "Invalid username or password");

        return new UserDTO(user.getUserId(), user.getUsername(), user.getUserRoles().getRoleName(), null);
    }

    @Override
    public void saveUser(UserDTO userDTO) {

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
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<UserDTO> filterUsers(String username) {
        return userRepository.filterUser(username);
    }

    @Override
    public UserDTO selectUser(long userId) {
        return userRepository.selectUser(userId)
                .orElseThrow(() -> new CustomException(404, "User not found"));
    }

    @Override
    public void updateUser(UserDTO userDTO) {

        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new CustomException(404, "User not found"));

        user.setUsername(userDTO.getUsername());

        if (userDTO.getUserRoles() != null) {
            Role role = roleRepository.findByRoleName(userDTO.getUserRoles())
                    .orElseThrow(() -> new CustomException(404, "Role not found: " + userDTO.getUserRoles()));
            user.setUserRoles(role);
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank())
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        if (!userRepository.existsById(userId))
            throw new CustomException(404, "User not found");
        userRepository.deleteById(userId);
    }
}
