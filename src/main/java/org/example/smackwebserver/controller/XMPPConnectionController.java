package org.example.smackwebserver.controller;

import org.example.smackwebserver.service.XMPPConnectionService;
import org.jivesoftware.smack.XMPPConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/xmpp")
public class XMPPConnectionController {

    private final XMPPConnectionService connectionService;

    @Autowired
    public XMPPConnectionController(XMPPConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    /**
     * 测试与 XMPP 服务器的连接
     * @return 连接状态
     */
    @GetMapping("/connect")
    public String testConnection() {
        try {
            XMPPConnection connection = connectionService.connect("admin", "BIT@1138");
            if (connection.isConnected()) {
                return "Successfully connected to XMPP server!";
            } else {
                return "Failed to connect to XMPP server.";
            }
        } catch (Exception e) {
            return "Error occurred while connecting to XMPP server: " + e.getMessage();
        }
    }

//    /**
//     * 登录到 XMPP 服务器
//     * @param username 用户名
//     * @param password 密码
//     * @return 登录状态
//     */
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        try {
//            connectionService.login(username, password);
//            return "Logged in successfully as " + username;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Login failed: " + e.getMessage();
//        }
//    }

    /**
     * 断开 XMPP 连接
     * @return 断开状态
     */
    @GetMapping("/disconnect")
    public String disconnect() {
        try {
            connectionService.disconnect();
            return "Disconnected from XMPP server.";
        } catch (Exception e) {
            return "Failed to disconnect: " + e.getMessage();
        }
    }
}

