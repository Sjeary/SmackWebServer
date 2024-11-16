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

    @GetMapping("/api/v1/User/{id}")
    public Response<UserDTO> getUserById(@PathVariable long id) {
        return Response.newSuccess(userService.getUserById(id)) ;
    }
    @GetMapping("/error")
    public String getUserError() {
        return """
                这是404页面
                /api/v1/User/{id} GET:用户查询信息的接口
                /api/v1/User/register POST:创建账号接口
                /api/v1/User/login POST:登陆接口
                """;
    }
    @PostMapping("/api/v1/User/register")
    public Response<Long> createUser(@RequestBody UserDTO userDTO) {
        return Response.newSuccess(userService.createUser(userDTO));
    }

    @PostMapping("/api/v1/User/login")
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
