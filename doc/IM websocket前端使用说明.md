# 即时通讯 websocket_smack 前端使用说明

## 文档总览

本文的目的是帮助前端了解这个~~手搓的~~即时通讯套接字**全双工通信原理、传输数据格式**。

## 内容

### 1. 通信方式

1.1 创建对象的URL：
```javascript
"ws://43.143.213.221:8080/websocket/"+userId
```
其中`userId`为**网站用户ID**，不是用户名，也不是什么邮箱什么的，是它的平台账户ID。为了确保唯一性，即时通讯数据库当中通通采用ID对用户进行标识，
且与原平台ID保持一致。

1.2 重载函数

前端需要在`javascript`中重写4个函数：`onopen onmessage onclose onerror`。

`onopen`：`WebSocket`对象定义时就会被调用（这并不意味着你不能在定义websocket之后重写`onopen`，因为js并非完全的顺序执行），一般你需要在
定义之后马上重写。

`onmessage`: 当我们的IM后端主动对你的浏览器发送消息时，该方法会被调用。即时接收消息便是依靠此函数实现。

`onclose`: websocket关闭时调用的函数。

`onerror`: websocket出现错误时调用的函数（目前还不知道如何打出报错）。

### 2. 消息格式

我们的消息为**纯文本消息**，同时采用`json`字符串的方式来进行前后端消息的传递。
其格式如下：
```javascript
{
    "senderId":111,
    "receiverId":222,
    "content":"Hiiiiiiiiiiii"
}
```
在传输时，你可以使用`JSON.parse`方法把服务器传给浏览器的消息解析成上述JSON格式，从中读取出sender, receiver, content等信息；也可以使用
`JSON.stringify`方法把用户想要发送的消息封装成JSON字符串发给后端，让其转发。

## 示例

一个简单的示例：[webIMDemo.html](../src/main/html/webIMDemo.html)