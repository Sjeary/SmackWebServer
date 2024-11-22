package org.example.smackwebserver.service;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class XMPPConnectionServiceImpl implements XMPPConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(XMPPConnectionService.class);

    private XMPPTCPConnection connection;

    @Override
    public String getUserName(long userId) {
        return String.format("web_user_%d", userId);
    }

    @Override
    public String getTagName(int tagId) {
        return String.format("web_tag_%d", tagId);
    }

    @Override
    public boolean isUserExists(long userId) {
        try {
            // 尝试登录
            connection.login(getUserName(userId), "password");
            return true;
        } catch (Exception e) {
            // 如果抛出异常，说明用户不存在
            return false;
        }
    }

    // 自动连接
    @Override
    public XMPPTCPConnection getConnection(long userId) {
        if (connection == null || !connection.isConnected()) {
            connect(userId);  // 如果连接为空或无效，则重新连接
        }
        return connection;
    }

    // Openfire连接和用户登录
    @Override
    public XMPPTCPConnection connect(long userId) {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setXmppDomain("localhost")
                    .setHost("120.46.26.49")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // 禁用 TLS 验证，生产环境不能用！
                    .build();

            connection = new XMPPTCPConnection(config);
            connection.connect();

            if (!isUserExists(userId)) {
                register(userId);
                connection.login(getUserName(userId), "password");
            }

            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Failed to login Openfire", e);
        }
    }

    // Openfire断开连接
    @Override
    public void disconnect() {
        if (connection != null && connection.isConnected()) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                throw new RuntimeException("Failed to quit Openfire", e);
            }
        }
    }

    // Openfire用户注册
    @Override
    public boolean register(long userId) {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setXmppDomain("localhost")
                    .setHost("120.46.26.49")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // 禁用 TLS 验证，生产环境不能用！
                    .build();

            connection = new XMPPTCPConnection(config);
            connection.connect();

            // 获取 XMPP 服务器上的账号管理器
            AccountManager accountManager = AccountManager.getInstance(connection);
            accountManager.sensitiveOperationOverInsecureConnection(true);

            // 设置用户的注册信息
            Localpart localpart = Localpart.from(getUserName(userId));
            accountManager.createAccount(localpart, "password");

            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to register Openfire user", e);
        }
    }
}

