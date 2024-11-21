package org.example.smackwebserver.service;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.springframework.stereotype.Service;

@Service
public class XMPPConnectionServiceImpl implements XMPPConnectionService {

    private XMPPTCPConnection connection;

    public XMPPTCPConnection connect(String username, String password) {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(username, password)
                    .setXmppDomain("localhost")
                    .setHost("120.46.26.49")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // 禁用 TLS 验证，生产环境不能用！
                    .build();

            connection = new XMPPTCPConnection(config);
            connection.connect();
            connection.login();
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        if (connection != null && connection.isConnected()) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

