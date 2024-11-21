# 新增接口文档

DynamicController和TagController的已实现部分的接口文档

```jsx
GET http://localhost:8080/api/v1/Dynamic/{id}
动态ID获取动态
response:
{
    "data": {
        "id": 1,
        "userId": 1,
        "title": "丽江深度游",
        "content": "我发布了一个“丽江深度游”产品",
        "urlId": "1",
        "issuedAt": "2024-11-19T20:34:10.121216",
        "updatedAt": "2024-11-19T20:34:10.121216",
        "tags": [
            {
                "id": 1,
                "name": "丽江"
            },
            {
                "id": 2,
                "name": "昆明"
            }
        ]
    },
    "errorMsg": null,
    "success": true
}
```

```jsx
POST http://localhost:8080/api/v1/Dynamic?tags=Travel&tags=Adventure
创建新的动态
request:
{
   
  "title": "hello",
  "content": "this is a test.",
  "userId": "1"
}

response:
{
    "data": {
        "id": 17,
        "userId": 1,
        "title": "hello",
        "content": "this is a test.",
        "urlId": null,
        "issuedAt": "2024-11-20T21:43:07.982228",
        "updatedAt": "2024-11-20T21:43:07.982271",
        "tags": [
            {
                "id": 5,
                "name": "Travel"
            },
            {
                "id": 6,
                "name": "Adventure"
            }
        ]
    },
    "errorMsg": null,
    "success": true
}
```

```jsx
PUT http://localhost:8080/api/v1/Dynamic/{id}?tags=yes,no
修改原先的动态内容，tags部分需要完全重新上传
request
{
   
  "title": "olleh",
  "content": "this is a test.",
  "userId": "1"
}
{
    "data": {
        "id": 17,
        "userId": 1,
        "title": "olleh",
        "content": "this is a test.",
        "urlId": null,
        "issuedAt": "2024-11-20T21:43:07.982228",
        "updatedAt": "2024-11-20T21:51:36.892738",
        "tags": [
            {
                "id": 8,
                "name": "no"
            },
            {
                "id": 7,
                "name": "yes"
            }
        ]
    },
    "errorMsg": null,
    "success": true
}
```

```jsx
DELETE http://localhost:8080/api/v1/Dynamic/{id}
用动态ID删除动态，如你所见，16号动态被删除了
response:
{
    "data": 16,
    "errorMsg": null,
    "success": true
}
```

```jsx
GET http://localhost:8080/api/v1/User/{user_id}/Dynamics
用户ID获取用户下属所有动态
response:
{
    "data": {
        "total_item": 16,
        "data": [
            {
                "id": 17,
                "userId": 1,
                "title": "olleh",
                "content": "this is a test.",
                "urlId": null,
                "issuedAt": "2024-11-20T21:43:07.982228",
                "updatedAt": "2024-11-20T21:51:36.892738",
                "tags": [
                    {
                        "id": 8,
                        "name": "no"
                    },
                    {
                        "id": 7,
                        "name": "yes"
                    }
                ]
            },
            {
                "id": 15,
                "userId": 1,
                "title": "测试",
                "content": "我发布了一个测试动态",
                "urlId": null,
                "issuedAt": "2024-11-20T21:34:07.881835",
                "updatedAt": "2024-11-20T21:34:07.881835",
                "tags": [
                    {
                        "id": 3,
                        "name": "test"
                    }
                ]
            },
            {
                "id": 14,
                "userId": 1,
                "title": "hello",
                "content": "this is a test.",
                "urlId": null,
                "issuedAt": "2024-11-20T21:32:42.791903",
                "updatedAt": "2024-11-20T21:32:42.791917",
                "tags": [
                    {
                        "id": 6,
                        "name": "Adventure"
                    },
                    {
                        "id": 5,
                        "name": "Travel"
                    }
                ]
            },
            {
                "id": 13,
                "userId": 1,
                "title": "hello",
                "content": "this is a test.",
                "urlId": null,
                "issuedAt": "2024-11-20T21:26:00.761912",
                "updatedAt": "2024-11-20T21:26:00.761925",
                "tags": [
                    {
                        "id": 6,
                        "name": "Adventure"
                    },
                    {
                        "id": 5,
                        "name": "Travel"
                    }
                ]
            },
            {
                "id": 12,
                "userId": 1,
                "title": "hello",
                "content": "this is a test.",
                "urlId": null,
                "issuedAt": "2024-11-20T21:18:04.059887",
                "updatedAt": "2024-11-20T21:18:04.059911",
                "tags": [
                    {
                        "id": 6,
                        "name": "Adventure"
                    },
                    {
                        "id": 5,
                        "name": "Travel"
                    }
                ]
            },
            {
                "id": 11,
                "userId": 1,
                "title": "hello",
                "content": "this is a test.",
                "urlId": null,
                "issuedAt": "2024-11-20T21:18:03.360355",
                "updatedAt": "2024-11-20T21:18:03.360474",
                "tags": [
                    {
                        "id": 6,
                        "name": "Adventure"
                    },
                    {
                        "id": 5,
                        "name": "Travel"
                    }
                ]
            },
            {
                "id": 10,
                "userId": 1,
                "title": "hello",
                "content": "this is a test.",
                "urlId": null,
                "issuedAt": "2024-11-20T21:17:34.83214",
                "updatedAt": "2024-11-20T21:17:34.832154",
                "tags": [
                    {
                        "id": 6,
                        "name": "Adventure"
                    },
                    {
                        "id": 5,
                        "name": "Travel"
                    }
                ]
            },
            {
                "id": 9,
                "userId": 1,
                "title": "测试",
                "content": "我发布了一个测试动态",
                "urlId": null,
                "issuedAt": "2024-11-20T20:51:17.88561",
                "updatedAt": "2024-11-20T20:51:17.88561",
                "tags": [
                    {
                        "id": 3,
                        "name": "test"
                    }
                ]
            },
            {
                "id": 8,
                "userId": 1,
                "title": "丽江深度游",
                "content": "我修改了一个“丽江深度游”产品",
                "urlId": null,
                "issuedAt": "2024-11-20T20:43:28.151112",
                "updatedAt": "2024-11-20T20:43:28.151112",
                "tags": [
                    {
                        "id": 4,
                        "name": "test1"
                    }
                ]
            },
            {
                "id": 7,
                "userId": 1,
                "title": "丽江深度游",
                "content": "我修改了一个“丽江深度游”产品",
                "urlId": null,
                "issuedAt": "2024-11-20T20:42:08.566874",
                "updatedAt": "2024-11-20T20:42:36.607174",
                "tags": [
                    {
                        "id": 3,
                        "name": "test"
                    }
                ]
            }
        ]
    },
    "errorMsg": null,
    "success": true
}
```

```jsx
GET http://localhost:8080/api/v1/Dynamic/{dynamic_id}/Comments
获取动态的评论
response:
{
    "data": [],
    "errorMsg": null,
    "success": true
}
```

```jsx
GET http://localhost:8080/api/v1/Spot/{spot_id}/Comments
获取景点的评论
response:
{
    "data": [],
    "errorMsg": null,
    "success": true
}
```

```jsx
GET http://localhost:8080/api/v1/Product/{product_id}/Comments
获取旅游产品的评论
{
    "data": [],
    "errorMsg": null,
    "success": true
}
```

```jsx
GET /api/v1/Tag
描述: 获取所有标签信息，支持分页。
请求参数:

参数名称	类型	是否必填	默认值	描述
page	int	否	0	页码，从0开始
size	int	否	10	每页数据量
response:
{
    "data": {
        "total_item": 8,
        "data": [
            {
                "id": 6,
                "name": "Adventure"
            },
            {
                "id": 8,
                "name": "no"
            },
            {
                "id": 3,
                "name": "test"
            },
            {
                "id": 4,
                "name": "test1"
            },
            {
                "id": 5,
                "name": "Travel"
            },
            {
                "id": 7,
                "name": "yes"
            },
            {
                "id": 1,
                "name": "丽江"
            },
            {
                "id": 2,
                "name": "昆明"
            }
        ]
    },
    "errorMsg": null,
    "success": true
}
```

```jsx
 GET /api/v1/Tag/search?keyword=丽江&page=0&size=10
 描述: 根据关键词搜索标签，支持分页。

请求参数:

参数名称	类型	是否必填	默认值	描述
keyword	String	是	无	搜索关键词
page	int	否	0	页码，从0开始
size	int	否	10	每页数据量
response
{
    "data": {
        "total_item": 1,
        "data": [
            {
                "id": 1,
                "name": "丽江"
            }
        ]
    },
    "errorMsg": null,
    "success": true
}
```

```jsx
GET /api/v1/Tag/{id}
描述: 根据标签 ID 获取关联动态信息，支持分页。

请求参数:

参数名称	类型	是否必填	默认值	描述
id	int	是	无	标签 ID
page	int	否	0	页码，从0开始
size	int	否	10	每页数据量

```

```jsx
GET http://localhost:8080/api/v1/Tag/{tag_id}?page=0&size=10
描述: 根据标签 ID 获取关联动态信息，支持分页。

请求参数:

参数名称	类型	是否必填	默认值	描述
id	int	是	无	标签 ID
page	int	否	0	页码，从0开始
size	int	否	10	每页数据量
response:
{
    "data": {
        "total_item": 3,
        "data": [
            {
                "id": 3,
                "userId": 1,
                "title": "丽江深度游",
                "content": "我发布了一个“丽江深度游”产品",
                "urlId": "1",
                "issuedAt": "2024-11-20T18:27:25.706059",
                "updatedAt": "2024-11-20T18:27:25.706059",
                "tags": [
                    {
                        "id": 1,
                        "name": "丽江"
                    },
                    {
                        "id": 2,
                        "name": "昆明"
                    }
                ]
            },
            {
                "id": 2,
                "userId": 1,
                "title": "丽江深度游",
                "content": "我发布了一个“丽江深度游”产品",
                "urlId": "1",
                "issuedAt": "2024-11-20T18:25:33.507577",
                "updatedAt": "2024-11-20T18:25:33.507577",
                "tags": [
                    {
                        "id": 1,
                        "name": "丽江"
                    },
                    {
                        "id": 2,
                        "name": "昆明"
                    }
                ]
            },
            {
                "id": 1,
                "userId": 1,
                "title": "丽江深度游",
                "content": "我发布了一个“丽江深度游”产品",
                "urlId": "1",
                "issuedAt": "2024-11-19T20:34:10.121216",
                "updatedAt": "2024-11-19T20:34:10.121216",
                "tags": [
                    {
                        "id": 1,
                        "name": "丽江"
                    },
                    {
                        "id": 2,
                        "name": "昆明"
                    }
                ]
            }
        ]
    },
    "errorMsg": null,
    "success": true
}
```

```jsx
GET /api/v1/TravelProduct/{id}/Comments
接口说明: 根据旅游产品的 ID 获取对应的评论（包括嵌套评论）。

请求参数:

参数名称	类型	必填	说明
id	int	是	旅游产品的 ID
{
    "data": [],
    "errorMsg": null,
    "success": true
}
```

```jsx
POST /api/v1/TravelProduct/{id}/Comments
接口说明: 给指定的旅游产品添加一条评论。

请求参数:

参数名称	类型	必填	说明
id	int	是	旅游产品的 ID
request:
{
    "content": "这是评论内容",
    "parentId": null,
    "replyId": null,
    "userId": 1
}

response:
{
    "data": {
        "id": 2,
        "userId": 1,
        "replyId": null,
        "content": "这是评论内容",
        "issuedAt": "2024-11-21T17:55:18.317903069",
        "parentId": 1,
        "replies": null
    },
    "errorMsg": null,
    "success": true
}
```