package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dto.UserDTO;
import org.example.smackwebserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/User/{id}")
    public Response<UserDTO> getUserById(@PathVariable long id) {
        try {
            return Response.newSuccess(userService.getUserById(id));
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }


    @PostMapping("/api/v1/User/register")
    public Response<Long> createUser(@RequestBody UserDTO userDTO) {
        try {
            // 调用服务层创建用户
            return Response.newSuccess(userService.createUser(userDTO));
        } catch (IllegalStateException e) {
            // 捕获 Email 重复异常，返回失败响应
            return Response.newFail(e.getMessage());
        }
    }


    @PostMapping("/api/v1/User/login/ById")
    public Response<String> loginUserById(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticateById(userDTO);
        if (isAuthenticated) {
            // 登录成功，返回一个会话令牌或成功消息
            String token = userService.generateToken(userDTO.getName());
            return Response.newSuccess(token);
        } else {
            // 登录失败
            return Response.newFail("Invalid username or password");
        }
    }

    @PostMapping("/api/v1/User/login/ByEmail")
    public Response<String> loginUserByEmail(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticateByEmail(userDTO);
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
