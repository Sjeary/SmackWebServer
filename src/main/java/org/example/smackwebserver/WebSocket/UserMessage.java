package org.example.smackwebserver.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * UserMessage
 *
 * 用于序列化、反序列化WebSocket传递的message消息。
 * 由于websocket只能传递纯文本or二进制消息，因此需要把senderId、receiverId、messageContent全部塞到一个message字符串中。
 */
public class UserMessage {
    private long senderId;
    private long receiverId;
    private String content;
    private Date date;

    private static Logger logger = LoggerFactory.getLogger(UserMessage.class);

    public static UserMessage genUserMessage(long senderId, long receiverId, String content, Date date) {
        UserMessage userMessage = new UserMessage();
        userMessage.setSenderId(senderId);
        userMessage.setReceiverId(receiverId);
        userMessage.setContent(content);
        userMessage.setDate(date);
        return userMessage;
    }

    public static String serializeUserMessage(UserMessage userMessage) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("senderId", userMessage.getSenderId());
        node.put("receiverId", userMessage.getReceiverId());
        node.put("content", userMessage.getContent());
        return node.toString();
    }

    public static UserMessage deserializeUserMessage(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(json);
        } catch(JsonProcessingException e) {
            logger.debug("Illegal JSON String for UserMessage: "+json);
            return new UserMessage();
        }
        UserMessage userMessage = new UserMessage();
        userMessage.setSenderId(node.get("senderId").asLong());
        userMessage.setReceiverId(node.get("receiverId").asLong());
        userMessage.setContent(node.get("content").asText());
        return userMessage;
    }

//    Getter and Setter
    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
