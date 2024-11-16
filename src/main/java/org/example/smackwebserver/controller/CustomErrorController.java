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
                <h3>/api/v1/TravelProduct/{id} GET:用产品ID拿到产品信息<br></h3>
                response:
                {
                    "data": {
                        "id": 1,
                        "userId": 1,
                        "title": "丽江深度游",
                        "startDate": "2024-12-01",
                        "endDate": "2024-12-07",
                        "features": "感受纳西族文化，体验玉龙雪山之美",
                        "theme": "文化体验",
                        "departureLocation": "昆明",
                        "destination": "丽江",
                        "maxCapacity": 20,
                        "productType": "跟团游",
                        "price": 1999.99,
                        "createdAt": "2024-11-17",
                        "updatedAt": "2024-11-17"
                    },
                    "errorMsg": null,
                    "success": true
                }
                后端进行了跨域访问配置 成功了
                """;
    }
}
