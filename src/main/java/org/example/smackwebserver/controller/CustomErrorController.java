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
                response:<br>
                {<br>
                    "data": {<br>
                        "id": 1,<br>
                        "userId": 1,<br>
                        "title": "丽江深度游",<br>
                        "startDate": "2024-12-01",<br>
                        "endDate": "2024-12-07",<br>
                        "features": "感受纳西族文化，体验玉龙雪山之美",<br>
                        "theme": "文化体验",<br>
                        "departureLocation": "昆明",<br>
                        "destination": "丽江",<br>
                        "maxCapacity": 20,<br>
                        "productType": "跟团游",<br>
                        "price": 1999.99,<br>
                        "createdAt": "2024-11-17",<br>
                        "updatedAt": "2024-11-17"<br>
                    },<br>
                    "errorMsg": null,<br>
                    "success": true<br>
                }<br>
                <br>
                <h3>/api/v1/TravelProduct POST:提交产品信息<br></h3>
                request:<br>
                {<br>
                    "userId": 1,<br>
                    "title": "丽江深度游",<br>
                    "startDate": "2024-12-01",<br>
                    "endDate": "2024-12-07",<br>
                    "features": "感受纳西族文化，体验玉龙雪山之美",<br>
                    "theme": "文化体验",<br>
                    "departureLocation": "昆明",<br>
                    "destination": "丽江",<br>
                    "maxCapacity": 20,<br>
                    "productType": "跟团游",<br>
                    "price": 1999.99<br>
                }<br>
                response:<br>
                {<br>
                    "data": 4,<br>
                    "errorMsg": null,<br>
                    "success": true<br>
                }<br>
                后端进行了跨域访问配置 成功了<br>
                """;
    }
}
