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
                                 "name": "postman",<br>
                                 "email": "postman@email.com",<br>
                                 "companyName": null,<br>
                                 "companyIntroduction": null,<br>
                                 "registerTime": "2024-11-17T21:38:25",<br>
                                 "homepageLink": null,<br>
                                 "passwd": null<br>
                             },<br>
                             "token": "TOKEN_null_1731861809939"<br>
                         },<br>
                         "errorMsg": null,<br>
                         "success": true<br>
                     }<br>
                
                
                <h3>/api/v1/User/login/ById POST:Id登陆接口<br></h3><br>
                request:{<br>
                    "id"<br>
                    "passwd"<br>
                }<br>
                response:<br>
                {<br>
                         "data": {<br>
                             "user": {<br>
                                 "id": 1,<br>
                                 "name": "postman",<br>
                                 "email": "postman@email.com",<br>
                                 "companyName": null,<br>
                                 "companyIntroduction": null,<br>
                                 "registerTime": "2024-11-17T21:38:25",<br>
                                 "homepageLink": null,<br>
                                 "passwd": null<br>
                             },<br>
                             "token": "TOKEN_null_1731861809939"<br>
                         },<br>
                         "errorMsg": null,<br>
                         "success": true<br>
                     }<br>
                <h3> /api/v1/User/{id} PUT:更新用户信息，其中不传入相关字段的话就不更新 </h3><br>
                Content-Type: application/json<br>
                request:<br>
                {<br>
                    "name": "新名字",<br>
                    "email": "newemail@example.com",<br>
                    "companyName": "新公司名称"<br>
                }<br>
                response:<br>
                {<br>
                    "data": 1,<br>
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
                        "url":"https://...",<br>
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
                    "price": 1999.99,<br>
                    "url":"https://..."<br>
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
                    "price": 2999.99,<br>
                    "url":"https://..."<br>
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
                
                <h3> /api/v1/TravelProduct/search  GET:通过筛选得到travel Product  </h3>
                使用场景：主页筛选符合条件的产品/用户列表展示自己所有发布的信息。<br>
                GET方法，可选参数：<br>
                userId  发布者的用户 ID<br>
                productType 产品类型<br>
                theme  产品主题<br>
                departureLocation 出发地<br>
                destination 目的地<br>
                page 分页页码，默认 0（第 1 页）<br>
                size 每页显示的数量，默认 10<br>
                不带参数：返回所有结果<br>
                带参数 ： 例如： GET /api/v1/TravelProduct/search?page=0&size=10<br>
                例如： GET /api/v1/TravelProduct/search?userId=1&productType=豪华跟团游&page=0&size=10<br>
                返回结果：<br>
                {<br>
                    "data": {<br>
                        "total_item": 25, // 总记录数<br>
                        "data": [<br>
                            {<br>
                                "id": 101,<br>
                                "userId": 1,<br>
                                "title": "丽江深度游升级版",<br>
                                "startDate": "2024-12-10",<br>
                                "endDate": "2024-12-15",<br>
                                "features": "升级住宿标准，体验玉龙雪山雪橇滑雪",<br>
                                "theme": "豪华体验",<br>
                                "departureLocation": "昆明",<br>
                                "destination": "丽江",<br>
                                "maxCapacity": 15,<br>
                                "productType": "豪华跟团游",<br>
                                "price": 2999.99,<br>
                                "createdAt": "2024-11-17",<br>
                                "updatedAt": "2024-11-17",<br>
                                "url":"https://..."<br>
                            }<br>
                        ]<br>
                    },<br>
                    "errorMsg": null,<br>
                    "success": true<br>
                }<br>
                
                /api/v1/TravelProduct/searchByKeyword?keyword=丽江&page=0&size=10 GET：使用关键字查询结果，返回存在于标题 主题 特色 出发地 目的地的结果<br>
                response:<br>
                {<br>
                    "data": {<br>
                        "content": [<br>
                            {<br>
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
                                "updatedAt": "2024-11-17",<br>
                                "url":"https://..."<br>
                            },<br>
                            {<br>
                                "id": 2,<br>
                                "userId": 2,<br>
                                "title": "云南大理自由行",<br>
                                "startDate": "2024-12-08",<br>
                                "endDate": "2024-12-14",<br>
                                "features": "苍山洱海，浪漫之旅",<br>
                                "theme": "休闲度假",<br>
                                "departureLocation": "昆明",<br>
                                "destination": "大理",<br>
                                "maxCapacity": 10,<br>
                                "productType": "自由行",<br>
                                "price": 2599.99,<br>
                                "createdAt": "2024-11-18",<br>
                                "updatedAt": "2024-11-18",<br>
                                "url":"https://..."<br>
                            }<br>
                        ],<br>
                        "totalPages": 3,<br>
                        "totalElements": 25,<br>
                        "size": 10,<br>
                        "number": 0,<br>
                        "numberOfElements": 2,<br>
                        "last": false,<br>
                        "first": true,<br>
                        "empty": false<br>
                    },<br>
                    "errorMsg": null,<br>
                    "success": true<br>
                }<br>
                Page 对象中的字段解释:<br>
                content	当前页的内容，是一个列表，包含具体的数据记录。<br>
                totalPages	总页数，表示结果可以分为多少页。<br>
                totalElements	总记录数，符合条件的结果的总条数。<br>
                size	每页的记录数，等于请求参数中的 size。<br>
                number	当前页的页码，从 0 开始。<br>
                numberOfElements	当前页的实际记录数，可能小于等于 size（最后一页时）。<br>
                last	是否是最后一页，布尔值。<br>
                first	是否是第一页，布尔值。<br>
                empty	当前页是否为空，布尔值。<br>
                后端进行了跨域访问配置 成功了<br>
                """;
    }
}
