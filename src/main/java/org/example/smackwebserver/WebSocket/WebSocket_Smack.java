package org.example.smackwebserver.WebSocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
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
import org.jxmpp.jid.impl.JidCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocket_Smack {

    private static final Logger logger = LoggerFactory.getLogger(WebSocket_Smack.class);
    public static AtomicInteger onlineCount = new AtomicInteger(0);
    public static List<WebSocket_Smack> webSockets = new CopyOnWriteArrayList<WebSocket_Smack>();

    private Session session;

    private XMPPTCPConnection connectionSender;
    private XMPPTCPConnection connectionReceiver;

    private long userId;

    public long getUserId() {
        return userId;
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") long userId) {  // OnOpen会有exception
        try {
            logger.debug("有新的websocket建立请求");
            if (userId == 0) {
                try {
                    session. close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
                return;
            }
            this.session = session;
            this.userId = userId;
            onlineCount.incrementAndGet();
            webSockets.add(this);
            logger.debug("新的websocket链接键入");

            // 配置 connection
            XMPPTCPConnectionConfiguration configSender = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(Long.toString(this.userId), "123456")
                    .setXmppDomain("sjeary")
                    .setResource("web") // 表示链接方式
                    .setHost(MySmackConfiguration.hostname)
                    .setPort(MySmackConfiguration.port)
                    .addEnabledSaslMechanism("PLAIN")
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();

            XMPPTCPConnectionConfiguration configReceiver = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(Long.toString(this.userId), "123456")
                    .setXmppDomain("sjeary")
                    .setHost(MySmackConfiguration.hostname)
                    .setPort(MySmackConfiguration.port)
                    .addEnabledSaslMechanism("PLAIN")
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();

            this.connectionSender = new XMPPTCPConnection(configSender);
            this.connectionReceiver = new XMPPTCPConnection(configReceiver);

            // 初始化、启动、登录 connection
            try {
                connectionSender.connect();
                connectionReceiver.connect();
                connectionSender.login();
                connectionReceiver.login();

                connectionReceiver.addAsyncStanzaListener(new StanzaListener() {
                    @Override
                    public void processStanza(Stanza packet) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
                        if(packet instanceof Message) {
                            Message message = (Message) packet;
                            UserMessage userMessage = new UserMessage();
                            userMessage.setSenderId(Long.getLong(message.getFrom().getLocalpartOrNull().toString()));
                            userMessage.setReceiverId(Long.getLong(message.getTo().getLocalpartOrNull().toString()));
                            userMessage.setContent(message.getBody());
                            userMessage.setDate(new Date());
                            sendMessage(UserMessage.serializeUserMessage(userMessage));
                        }
                    }
                }, MessageWithBodiesFilter.INSTANCE);
            } catch (Exception e) {
                logger.error("Error opening connection for UserId: "+this.userId+": "+e.getMessage());
                try {
                    this.session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, e.getMessage()));
                } catch (IOException e1) {
                    logger.error("Error closing session: " + e1.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            connectionSender.disconnect();
            connectionReceiver.disconnect();
            return;
        }
    }

    @OnClose
    public void onClose() {
        connectionSender.disconnect();
        connectionReceiver.disconnect();

        onlineCount.decrementAndGet();
        webSockets.remove(this);
        logger.debug("有链接关闭");
    }

    /**
     *
     * OnMessage
     * 客户端向服务端调用onMessage时被调用的函数。
     * 当客户端想要通过IM向另一个用户发消息时，使用此函数。
     *
     * @param message: 是UserMessage类的序列化。
     * @param session
     * @param userId: 接收者ID
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") long userId) {
        logger.debug("来自 UserId:"+userId+"的消息: "+message);
        UserMessage userMessage = UserMessage.deserializeUserMessage(message);
        if(userMessage.getSenderId() != userId){
            logger.debug("非法的 message 发送。");
            return;
        }

        try {
            Message stanzaMessage = MessageBuilder.buildMessage()
                    .to(JidCreate.entityBareFrom(Long.toString(userMessage.getReceiverId())+"@"+MySmackConfiguration.domainName+"/web"))
                    .from(connectionSender.getUser())
                    .setBody(userMessage.getContent())
                    .build();
            connectionSender.sendStanza(stanzaMessage);
            pushMessage(userId, UserMessage.serializeUserMessage(userMessage), userMessage.getReceiverId());
        } catch (Exception e) {
            logger.error("onMessage 消息推送失败 error: " + e.getMessage());
        }
    }

    public void pushMessage(long senderId, String message, long receiverId) {
        if(receiverId == 0) {
            logger.debug("Invalid ReceiverId: "+receiverId);
            return;
        }
        for(WebSocket_Smack webSocket : webSockets) {
            if(webSocket.getUserId() == receiverId) {
                logger.debug("成功转发一个消息");
                webSocket.sendMessage(message);
            }
        }
    }
}
