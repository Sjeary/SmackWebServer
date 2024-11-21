package org.example.smackwebserver.service;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public interface XMPPConnectionService {
    XMPPTCPConnection connect(String username, String password);

    public void disconnect();

}
