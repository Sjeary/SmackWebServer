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
                
                <h3>/api/v1/User/{id} GET:用户查询信息的接口<br></h3>
                
                <h3>/api/v1/User/register POST:创建账号接口<br></h3>
                request:{<br>
                    "name"<br>
                    "email"<br>
                    "passwd"<br>
                }<br>
                response:{<br>
                    "data":(生成的id)<br>
                    "errorMsg"<br>
                    "success"<br>
                }<br>
                <h3>/api/v1/User/login/ByEmail  POST:邮箱登陆接口<br></h3>
                request:{<br>
                    "email"<br>
                    "passwd"<br>
                }<br>
                response:{<br>
                    "data":(如果success，是生成的token)<br>
                    "errorMsg"<br>
                    "success"<br>
                }
                <h3>/api/v1/User/login/ById POST:Id登陆接口<br></h3>
                request:{<br>
                    "id"<br>
                    "passwd"<br>
                }<br>
                response:{<br>
                    "data":(如果success，是生成的token)<br>
                    "errorMsg"<br>
                    "success"<br>
                }<br>
                后端进行了跨域访问配置 成功了
                """;
    }
}
