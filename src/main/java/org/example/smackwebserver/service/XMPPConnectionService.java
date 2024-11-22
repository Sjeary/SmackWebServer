package org.example.smackwebserver.service;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

// 连接Openfire服务器
public interface XMPPConnectionService {
    String getUserName(long userId);

    String getTagName(int tagId);

    boolean isUserExists(long userId);

    // 自动连接
    XMPPTCPConnection getConnection(long userId);

    XMPPTCPConnection connect(long userId);

    void disconnect();

    boolean register(long userId);
}
