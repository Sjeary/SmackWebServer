package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.User;
import org.example.smackwebserver.dto.LoginResponse;
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
    public Response<LoginResponse> loginUserById(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticateById(userDTO);
        if (isAuthenticated) {
            // 登录成功，生成 token 并返回用户信息和 token
            UserDTO fullUserInfo = userService.getUserById(userDTO.getId());
            String token = userService.generateToken(fullUserInfo);
            LoginResponse loginResponse = new LoginResponse(fullUserInfo, token);
            return Response.newSuccess(loginResponse);
        } else {
            // 登录失败
            return Response.newFail("Invalid username or password");
        }
    }


    @PostMapping("/api/v1/User/login/ByEmail")
    public Response<LoginResponse> loginUserByEmail(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticateByEmail(userDTO);
        if (isAuthenticated) {
            // 登录成功，生成 token 并返回用户信息和 token
            UserDTO fullUserInfo = userService.getUserByEmail(userDTO.getEmail());
            String token = userService.generateToken(fullUserInfo);
            LoginResponse loginResponse = new LoginResponse(fullUserInfo, token);
            return Response.newSuccess(loginResponse);
        } else {
            // 登录失败
            return Response.newFail("Invalid email or password");
        }
    }

    @PutMapping("/api/v1/User/{id}")
    public Response<Long> updateUser(
            @PathVariable("id") long id,
            @RequestBody User user) {
        try {
            user.setId(id); // 确保用户 ID 是正确的
            Long updatedId = userService.updateUser(user);
            return Response.newSuccess(updatedId);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to update user: " + e.getMessage());
        }
    }

}
