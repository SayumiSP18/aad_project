package com.example.aad_project.repository;

import com.example.aad_project.dto.UserDTO;
import com.example.aad_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserNameAndPassword(String username, String password);

    Optional<User> findByUserName(String username);

    @Query(value = "SELECT new com.example.aad_project.dto.UserDTO(u.userId,u.userName,u.userRoles) " +
            "FROM User u")
    List<UserDTO> getAllUsers();

    @Query(value = "SELECT new com.example.aad_project.dto.UserDTO(u.userId,u.userName,u.userRoles) " +
            "FROM User u " +
            "WHERE (?1 IS NULL OR u.userName LIKE %?1%)")
    List<UserDTO> filterUser(String username);

    @Query(value = "SELECT new com.example.aad_project.dto.UserDTO(u.userId,u.userName,u.userRoles) " +
            "FROM User u WHERE u.userId=?1")
    UserDTO selectUser(long userId);}
