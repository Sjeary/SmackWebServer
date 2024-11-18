package org.example.smackwebserver.WebSocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    public void onOpen(Session session, @PathParam("userId") long userId) {
        logger.debug("有新的websocket建立请求");
        if (userId == 0) {
            try {
                session. close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        this.session = session;
        this.userId = userId;
        onlineCount.incrementAndGet();
        webSockets.add(this);
        logger.debug("新的websocket链接键入");
    }

    @OnClose
    public void onClose() {
        onlineCount.decrementAndGet();
        webSockets.remove(this);
        logger.debug("有链接关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") long userId) {
        logger.debug("来自 UserId:"+userId+"的消息: "+message);
        UserMessage userMessage = UserMessage.deserializeUserMessage(message);
        if(userMessage.getSenderId() != userId){
            logger.debug("非法的 message 发送。");
            return;
        }
        pushMessage(userId, message, userMessage.getReceiverId());
    }

    public void broadcastMessage(String message) {
        for(WebSocket_Smack webSocket : webSockets) {
            webSocket.sendMessage(message);
        }
    }

    public void pushMessage(long senderId, String message, long receiverId) {
        if(receiverId == 0) {
            broadcastMessage(message);
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
