package org.example.smackwebserver.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String getUserError() {
        return """
                这是404页面<br>
                
                /api/v1/User/{id} GET:用户查询信息的接口<br>
                
                /api/v1/User/register POST:创建账号接口<br>
                
                /api/v1/User/login/ByEmail  POST:邮箱登陆接口<br>
                
                /api/v1/User/login/ById POST:Id登陆接口<br>
                
                后端进行了跨域访问配置 成功了
                """;
    }
}
