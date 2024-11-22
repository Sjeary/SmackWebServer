package org.example.smackwebserver.controller;

import org.example.smackwebserver.service.XMPPConnectionService;
import org.jivesoftware.smack.XMPPConnection;
import org.springframework.web.bind.annotation.*;

// 仅测试用，非正式接口
@RestController
@RequestMapping("api/v1/xmpp")
public class XMPPConnectionController {

    private final XMPPConnectionService connectionService;

    public XMPPConnectionController(XMPPConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    /**
     * 测试与 XMPP 服务器的连接
     * @return 连接状态
     */
    @GetMapping("/connect")
    public String testConnection(@RequestParam long userId) {
        try {
            XMPPConnection connection = connectionService.connect(userId);
            if (connection.isConnected()) {
                return "Successfully connected to XMPP server!";
            } else {
                return "Failed to connect to XMPP server.";
            }
        } catch (Exception e) {
            return "Error occurred while connecting to XMPP server: " + e.getMessage();
        }
    }

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

    /**
     * 注册 XMPP 用户
     * @return 注册状态
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam long userId) {
        boolean result = connectionService.register(userId);
        if (result) {
            return "User registered successfully!";
        } else {
            return "Failed to register user.";
        }
    }
}

