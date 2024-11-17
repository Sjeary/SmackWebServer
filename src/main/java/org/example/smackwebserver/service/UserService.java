package org.example.smackwebserver.service;

import org.example.smackwebserver.dto.UserDTO;

public interface UserService
{
    UserDTO getUserById(long id);

    Long createUser(UserDTO userDTO);

    boolean authenticateById(UserDTO userDTO);

    boolean authenticateByEmail(UserDTO userDTO);

    String generateToken(String name);

    UserDTO getUserByEmail(String email);
}
