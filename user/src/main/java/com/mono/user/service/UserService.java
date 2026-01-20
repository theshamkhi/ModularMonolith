package com.mono.user.service;

import com.mono.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    boolean isUserActive(Long id);
}