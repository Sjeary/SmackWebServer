package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.PubMessage;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.MessageBuilder;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmackPubSubServiceImpl implements SmackPubSubService, InitializingBean {
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
    private String domainName = "localhost";

    private PubSubManager pubSubManager;
    private XMPPTCPConnection adminConnection;

    private Logger logger = LoggerFactory.getLogger(SmackPubSubServiceImpl.class);

    @Override
    public void afterPropertiesSet() {
        try{
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost(hostname)
                    .setPort(Integer.parseInt(port))
                    .setXmppDomain(domainName)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // 禁用 TLS 验证，生产环境不能用！
                    .setUsernameAndPassword(this.adminName, this.adminPassword)
                    .build();
            System.out.println("[PubSubManager Config] adminName: " + adminName +" adminPassword: " + adminPassword);
            this.adminConnection = new XMPPTCPConnection(config);
            this.adminConnection.connect();
            this.adminConnection.login();

            this.pubSubManager = PubSubManager.getInstanceFor(this.adminConnection);}
        catch (Exception e) {
            logger.error("Construct error: {}", e.getMessage());
        }
    }

    @Override
    public void createUserNode(long userId) {
        try {
            String nodeId = "user_"+userId;
            LeafNode node = pubSubManager.createNode(nodeId);
            Jid adminJid = JidCreate.entityBareFrom(adminName+"@"+domainName);
            Jid userJid = JidCreate.entityBareFrom("web_user_"+userId+"@"+domainName);
            node.addItemEventListener(new ItemEventListener<>() {
                @Override
                public void handlePublishedItems(ItemPublishEvent itemPublishEvent) {
                    for(Object item: itemPublishEvent.getItems()) {
                        if(item instanceof PubMessage) {
                            Message message = MessageBuilder.buildMessage()
                                    .from(adminJid)
                                    .to(userJid)
                                    .setBody(((PubMessage) item).getMessage())
                                    .build();
                            try {
                                adminConnection.sendStanza(message);
                            } catch (Exception e) {
                                logger.error("cannot send pubmessage from user node to user account: "+e.getMessage());
                            }
                        }
                    }
                }
            });
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

    private void createTagNode(String tag) {
        LeafNode node;
        try {
            node = pubSubManager.getLeafNode("tag_"+tag);
        } catch (PubSubException.NotAPubSubNodeException e) {
            try {
                node = pubSubManager.createNode("user_"+tag);
            } catch(Exception e1) {
                logger.error("create tag node failed: "+e1.getMessage());
                return;
            }
        } catch (Exception e){
            logger.error("getTagNode error."+e.getMessage());
        }
    }

    @Override
    public void subscribeTag(long userNodeId, String tag) {
        this.subscribe(userNodeId, "tag_"+tag);
    }

    @Override
    public void subscribeUser(long userNodeId, long subscribedUserId) {
        if (userService.getUserById(userNodeId) == null) {
            logger.error("user does not exist: ",+subscribedUserId);
            return;
        }
        this.subscribe(userNodeId, "user_"+subscribedUserId);
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
            node.unsubscribe("user_"+userNodeId);
        } catch (Exception e) {
            logger.error("unsubscribe error: cannot unsubscribe from the node: "+e.getMessage());
        }
    }

    @Override
    public void unsubscribeTag(long userNodeId, String tag) {
        unsubscribe(userNodeId, "tag_"+tag);
    }

    @Override
    public void unsubscribeUser(long userNodeId, long subscribedUserId) {
        unsubscribe(userNodeId,"user_"+subscribedUserId);
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
            Item item = new PubMessage(nodeId, message);
            node.publish(item);
        } catch(Exception e){
            logger.error("publishMessage error: cannot publish message: "+e.getMessage());
        }
    }

    @Override
    public void publishMessageToTagNode(String tag, String message) {
        publishMessage("tag_"+tag, "[pub message] A pub message for tag node: "+message);
    }

    @Override
    public void publishMessageToUserNode(long userId, String message) {
        publishMessage("user_"+userId, message);
    }
}
