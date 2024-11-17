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
        userDTO.setCompanyName(user.getCompanyName());
        userDTO.setCompanyIntroduction(user.getCompanyIntroduction());
        userDTO.setHomepageLink(user.getHomepageLink());
        userDTO.setRegisterTime(user.getRegisterTime());
        return userDTO;
    }
    public static User convertUser(UserDTO userDTO)
    {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPasswd(userDTO.getPasswd());
        user.setCompanyName(userDTO.getCompanyName());
        user.setCompanyIntroduction(userDTO.getCompanyIntroduction());
        user.setHomepageLink(userDTO.getHomepageLink());
        user.setRegisterTime(userDTO.getRegisterTime());
        return user;
    }
}
