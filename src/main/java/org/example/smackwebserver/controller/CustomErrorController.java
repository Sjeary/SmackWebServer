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
                response:<br>
                {<br>
                    "data": {<br>
                        "user": {<br>
                            "id": 1,<br>
                            "name": "JohnDoe",<br>
                            "passwd": null,<br>
                            "email": "john.doe@example.com",<br>
                            "age": 25<br>
                        },<br>
                        "token": "TOKEN_JohnDoe_1699801234567"<br>
                    },<br>
                    "errorMsg": null,<br>
                    "success": true<br>
                }<br>
                
                <h3>/api/v1/User/login/ById POST:Id登陆接口<br></h3>
                request:{<br>
                    "id"<br>
                    "passwd"<br>
                }<br>
                response:<br>
                {<br>
                    "data": {<br>
                        "user": {<br>
                            "id": 1,<br>
                            "name": "JohnDoe",<br>
                            "passwd": null,<br>
                            "email": "john.doe@example.com",<br>
                            "age": 25<br>
                        },<br>
                        "token": "TOKEN_JohnDoe_1699801234567"<br>
                    },<br>
                    "errorMsg": null,<br>
                    "success": true<br>
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
                <h3>/api/v1/TravelProduct/{id} PUT: 更新产品信息</h3><br>
                请求 URL:<br>
                PUT /api/v1/TravelProduct/{id}<br>
                请求参数：<br>
                id（路径参数）：需要更新的产品 ID。<br>
                请求体（JSON 格式）：<br>
                {<br>
                    "title": "丽江深度游升级版",<br>
                    "startDate": "2024-12-10",<br>
                    "endDate": "2024-12-15",<br>
                    "features": "升级住宿标准，体验玉龙雪山雪橇滑雪",<br>
                    "theme": "豪华体验",<br>
                    "departureLocation": "昆明",<br>
                    "destination": "丽江",<br>
                    "maxCapacity": 15,<br>
                    "productType": "豪华跟团游",<br>
                    "price": 2999.99<br>
                }<br>
                说明：<br>
                
                id 为路径参数，用于指定更新的产品。<br>
                请求体中需要包含需要更新的字段，未传递的字段将保持原值。<br>
                <br>
                响应:<br>
                成功响应：<br>
                {<br>
                    "data": 1, // 表示被更新的产品 ID<br>
                    "errorMsg": null,<br>
                    "success": true<br>
                }<br>
                失败响应:<br>
                {<br>
                    "data": null,<br>
                    "errorMsg": "Travel product with ID 1 does not exist",<br>
                    "success": false<br>
                }<br>
                
                <h3>/api/v1/TravelProduct/{id} DELETE: 删除产品信息</h3><br>
                请求<br>
                请求 URL:<br>
                DELETE /api/v1/TravelProduct/{id}<br>
                请求参数：<br>
                id（路径参数）：需要删除的产品 ID。<br>
                响应<br>
                成功响应:<br>
                {<br>
                    "data": 1,<br>
                    "errorMsg": null,<br>
                    "success": true<br>
                }<br>
                失败相应：<br>
                {<br>
                    "data": null,<br>
                    "errorMsg": "Travel product with ID 1 does not exist",<br>
                    "success": false<br>
                }<br>
                
                
                后端进行了跨域访问配置 成功了<br>
                """;
    }
}
