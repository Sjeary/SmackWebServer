package org.example.smackwebserver.dao;

import org.jivesoftware.smackx.pubsub.Item;

public class PubMessage extends Item {
    private String pubNodeId; // 发布订阅的node Id
    private String message;

    public PubMessage() {message = "";pubNodeId = "";}

    public PubMessage(String pubNodeId, String message) {
        this.message = message;
        this.pubNodeId = pubNodeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPubNodeId() {
        return pubNodeId;
    }

    public void setPubNodeId(String pubNodeId) {
        this.pubNodeId = pubNodeId;
    }
}
