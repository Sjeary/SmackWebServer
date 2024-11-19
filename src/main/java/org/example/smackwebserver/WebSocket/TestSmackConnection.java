package org.example.smackwebserver.WebSocket;

import jakarta.websocket.CloseReason;
import org.example.smackwebserver.config.MySmackConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.filter.MessageWithBodiesFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.MessageBuilder;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

public class TestSmackConnection {
    public static Logger logger = LoggerFactory.getLogger(TestSmackConnection.class);

    public static void main(String[] args) throws Exception{
        XMPPTCPConnection connectionSender;
        XMPPTCPConnection connectionReceiver;

        XMPPTCPConnectionConfiguration configSender = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(Long.toString(111), "123456")
                .setXmppDomain("sjeary")
                .setResource("web") // 表示链接方式
                .setHost(MySmackConfiguration.hostname)
                .setPort(MySmackConfiguration.port)
                .addEnabledSaslMechanism("PLAIN")
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();

        XMPPTCPConnectionConfiguration configReceiver = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(Long.toString(111), "123456")
                .setXmppDomain("sjeary")
                .setHost(MySmackConfiguration.hostname)
                .setPort(MySmackConfiguration.port)
                .addEnabledSaslMechanism("PLAIN")
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();

        connectionSender = new XMPPTCPConnection(configSender);
        connectionReceiver = new XMPPTCPConnection(configReceiver);

        try {
            logger.info("开始建立链接");
            connectionSender.connect();
            connectionReceiver.connect();
            logger.info("链接建立完毕，开始登录");
            connectionSender.login();
            connectionReceiver.login();

            connectionReceiver.addAsyncStanzaListener(new StanzaListener() {
                @Override
                public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                    if(packet instanceof Message) {
                        System.out.println("New Message: "+((Message) packet).getBody());
                    }
                }
            }, MessageWithBodiesFilter.INSTANCE);
        } catch (Exception e) {
            logger.error("Error opening connection for UserId: "+111+": "+e.getMessage());
        }

        logger.info("开始发消息");
        Message message = MessageBuilder.buildMessage().to(connectionReceiver.getUser()).from(connectionSender.getUser()).setBody("Hi, this is a test message.").build();
        connectionSender.sendStanza(message);
        Thread.sleep(2000);

        connectionSender.disconnect();
        connectionReceiver.disconnect();
    }
}