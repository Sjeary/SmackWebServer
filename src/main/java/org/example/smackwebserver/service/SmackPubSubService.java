package org.example.smackwebserver.service;

public interface SmackPubSubService {
    void createUserNode(int userId);

    public void subscribe(int userNodeId, String subscribedNodeId);
    /**
     * 用于订阅tag
     * @param userNodeId
     * @param tag
     */
    public void subscribeTag(int userNodeId, String tag);

    /**
     * 用于订阅用户
     * @param userNodeId
     * @param subscribedUserId
     */
    public void subscribeUser(int userNodeId, int subscribedUserId);

    public void unsubscribe(int userNodeId, String subscribedNodeId);

    /**
     * 取消对一个tag的订阅
     * @param userNodeId
     * @param tag
     */
    public void unsubscribeTag(int userNodeId, String tag);
    /**
     * 取消对一个用户的订阅
     */
    public void unsubscribeUser(int userNodeId, int subscribedUserId);

    public void publishMessage(String nodeId, String message);

    public void publishMessageToTagNode(String tag, String message);

    public void publishMessageToUserNode(int userId, String message);
}
