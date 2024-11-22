package org.example.smackwebserver.service;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.pubsub.*;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmackPubSubServiceImpl implements SmackPubSubService {
    @Autowired
    private UserService userService;

    @Value("${openfire.hostname}")
    private String hostname;
    @Value("${openfire.port}")
    private String port;
    @Value("${openfire.admin_name}")
    private String adminName;
    @Value("${openfire.admin_password}")
    private String adminPassword;
    private String domainName = "sjeary";

    private PubSubManager pubSubManager;
    private XMPPTCPConnection connection;

    private Logger logger = LoggerFactory.getLogger(SmackPubSubServiceImpl.class);

    public SmackPubSubServiceImpl() throws Exception {
        try{XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost("43.143.213.221")
                .setXmppDomain("sjeary")
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // 禁用 TLS 验证，生产环境不能用！
                .setUsernameAndPassword(this.adminName, this.adminPassword)
                .build();
            this.connection = new XMPPTCPConnection(config);
            this.connection.connect();
            this.connection.login();

            this.pubSubManager = PubSubManager.getInstanceFor(this.connection);}
        catch (Exception e) {
            logger.error("Construct error: {}", e.getMessage());
        }

    }

    @Override
    public void createUserNode(long userId) {
        try {
            pubSubManager.createNode("user_"+userId);
        } catch (Exception e) {
            logger.error("can't create user node:"+e.getMessage());
        }
    }

    @Override
    public void subscribe(long userNodeId, String subscribedNodeId) {
        Jid userJid = JidCreate.bareFromOrNull("user_"+userNodeId+this.domainName);
        try {
            LeafNode node = pubSubManager.getOrCreateLeafNode(subscribedNodeId);
            node.subscribe(userJid);
        } catch (Exception e) {
            logger.error("subscribe error.");
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void subscribeTag(long userNodeId, String tag) {
        this.subscribe(userNodeId, tag);
    }

    @Override
    public void subscribeUser(long userNodeId, long subscribedUserId) {
        if (userService.getUserById(userNodeId) == null) {
            logger.error("user does not exist: ",+subscribedUserId);
            return;
        }
        this.subscribe(userNodeId, "user_"+subscribedUserId+"@"+this.domainName);
    }

    @Override
    public void unsubscribe(long userNodeId, String subscribedNodeId) {
        LeafNode node;
        try {
            node = pubSubManager.getLeafNode(subscribedNodeId);
        } catch (Exception e) {
            logger.error("unsubscribe error: cannot find subscribedNode: "+e.getMessage());
            return;
        }
        try {
            node.unsubscribe("user_"+userNodeId+"@"+this.domainName);
        } catch (Exception e) {
            logger.error("unsubscribe error: cannot unsubscribe from the node: "+e.getMessage());
        }
    }

    @Override
    public void unsubscribeTag(long userNodeId, String tag) {
        unsubscribe(userNodeId, tag);
    }

    @Override
    public void unsubscribeUser(long userNodeId, long subscribedUserId) {
        unsubscribe(userNodeId,"user_"+subscribedUserId+"@"+this.domainName);
    }

    @Override
    public void publishMessage(String nodeId, String message) {
        LeafNode node = null;
        try {
            node = pubSubManager.getLeafNode(nodeId);
        } catch (Exception e) {
            logger.error("cannot find node to publish message: "+e.getMessage());
            return;
        }
        try{
            Item item = new Item(message);
            node.publish(item);
        } catch(Exception e){
            logger.error("publishMessage error: cannot publish message: "+e.getMessage());
        }
    }

    @Override
    public void publishMessageToTagNode(String tag, String message) {
        publishMessage("tag_"+tag, message);
    }

    @Override
    public void publishMessageToUserNode(long userId, String message) {
        publishMessage("user_"+userId, message);
    }
}
