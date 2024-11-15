package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.User;
import org.example.smackwebserver.dto.UserDTO;
import org.example.smackwebserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/User/{id}")
    public Response<UserDTO> getUserById(@PathVariable long id) {
        return Response.newSuccess(userService.getUserById(id)) ;
    }

    @PostMapping("/User")
    public Response<Long> createUser(@RequestBody UserDTO userDTO) {
        return Response.newSuccess(userService.createUser(userDTO));
    }

    @PostMapping("/User/login")
    public Response<String> loginUser(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticate(userDTO);
        if (isAuthenticated) {
            // 登录成功，返回一个会话令牌或成功消息
            String token = userService.generateToken(userDTO.getName());
            return Response.newSuccess(token);
        } else {
            // 登录失败
            return Response.newFail("Invalid username or password");
        }
    }

}
