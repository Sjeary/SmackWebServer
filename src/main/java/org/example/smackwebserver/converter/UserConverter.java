package org.example.smackwebserver.converter;

import org.example.smackwebserver.dao.User;
import org.example.smackwebserver.dto.UserDTO;

public class UserConverter {
    public static UserDTO convertUser(User user)
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
    public static User convertUser(UserDTO userDTO)
    {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        user.setPasswd(userDTO.getPasswd());
        return user;
    }
}
