package org.example.smackwebserver.service;

public interface SmackPubSubService {
    void afterPropertiesSet();

    void createUserNode(long userId);

    public void subscribe(long userNodeId, String subscribedNodeId);
    /**
     * 用于订阅tag
     * @param userNodeId
     * @param tag
     */
    public void subscribeTag(long userNodeId, String tag);

    /**
     * 用于订阅用户
     * @param userNodeId
     * @param subscribedUserId
     */
    public void subscribeUser(long userNodeId, long subscribedUserId);

    public void unsubscribe(long userNodeId, String subscribedNodeId);

    /**
     * 取消对一个tag的订阅
     * @param userNodeId
     * @param tag
     */
    public void unsubscribeTag(long userNodeId, String tag);
    /**
     * 取消对一个用户的订阅
     */
    public void unsubscribeUser(long userNodeId, long subscribedUserId);

    public void publishMessage(String nodeId, String message);

    public void publishMessageToTagNode(String tag, String message);

    public void publishMessageToUserNode(long userId, String message);
}
