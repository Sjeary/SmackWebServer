package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.User;
import org.example.smackwebserver.dto.UserDTO;

public interface UserService
{
    UserDTO getUserById(long id);

    Long createUser(UserDTO userDTO);

    boolean authenticate(UserDTO userDTO);

    String generateToken(String name);
}
