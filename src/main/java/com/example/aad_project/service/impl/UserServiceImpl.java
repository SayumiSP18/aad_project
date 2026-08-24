package com.example.aad_project.service.impl;

import com.example.aad_project.dto.UserDTO;
import com.example.aad_project.entity.User;
import com.example.aad_project.exception.CustomException;
import com.example.aad_project.repository.UserRepository;
import com.example.aad_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserDetails(String username, String password) {

        Optional<User> optionalUser = userRepository.findByUserNameAndPassword(username,password);
        if(optionalUser.isEmpty())
            throw new CustomException(404,"User not found");

        User user = optionalUser.get();
        return new UserDTO(user.getUserId(),user.getUsername(),user.getUserRole(),user.getPassword());

    }

    @Override
    public void saveUser(UserDTO userDTO) {

        if(userDTO.getUserRoles().equals(""))
            throw new CustomException(404,"User Role cannot be empty");

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setUserRoles(userDTO.getUserRole());

        userRepository.save(user);
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
        return userRepository.selectUser(userId);
    }

    @Override
    public void updateUser(UserDTO userDTO) {

        Optional<User> optionalUser = userRepository.findById(userDTO.getUserId());
        if(optionalUser.isEmpty())
            throw new RuntimeException("Sorry no user");

        User user = optionalUser.get();

        user.setUsername(userDTO.getUsername());
        user.setUserRoles(userDTO.getUserRole());

        userRepository.save(user);

    }
}
