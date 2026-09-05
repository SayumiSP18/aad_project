package com.example.aad_project.service;

import com.example.aad_project.dto.AuthDTO;
import com.example.aad_project.dto.UserDTO;

import java.util.List;

public interface UserService {


    UserDTO authenticate(AuthDTO authDTO);

    void saveUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserDetails(String username, String password);

    List<UserDTO> filterUsers(String username);

    UserDTO selectUser(long userId);

    void updateUser(UserDTO userDTO);

    void deleteUser(long userId);}
