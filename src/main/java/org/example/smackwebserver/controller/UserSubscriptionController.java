package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.UserSubscription;
import org.example.smackwebserver.service.SmackPubSubService;
import org.example.smackwebserver.service.UserService;
import org.example.smackwebserver.service.UserSubscriptionService;
import org.example.smackwebserver.service.XMPPConnectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/UserSubUser")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;
    private final XMPPConnectionService xmppConnectionService;
    private final SmackPubSubService pubSubService;

    public UserSubscriptionController(UserSubscriptionService userSubscriptionService, XMPPConnectionService xmppConnectionService, SmackPubSubService pubSubService) {
        this.userSubscriptionService = userSubscriptionService;
        this.xmppConnectionService = xmppConnectionService;
        this.pubSubService = pubSubService;
    }

    @PostMapping("/{userIdFrom}/{userIdTo}")
    public Response<UserSubscription> addSubscription(@PathVariable Long userIdFrom, @PathVariable Long userIdTo) {
        try {
            UserSubscription subscription = userSubscriptionService.addSubscription(userIdFrom, userIdTo);
            xmppConnectionService.getConnection(userIdFrom);
            pubSubService.subscribeUser(userIdFrom, userIdTo);
            return Response.newSuccess(subscription);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }

    @DeleteMapping("/{userIdFrom}/{userIdTo}")
    public Response<String> removeSubscription(@PathVariable Long userIdFrom, @PathVariable Long userIdTo) {
        try {
            userSubscriptionService.removeSubscription(userIdFrom, userIdTo);
            xmppConnectionService.getConnection(userIdFrom);
            pubSubService.unsubscribeUser(userIdFrom, userIdTo);
            return Response.newSuccess("Subscription removed successfully.");
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }

    @GetMapping("/{userId}/subscriptions")
    public Response<List<UserSubscription>> getUserSubscriptions(@PathVariable Long userId) {
        try {
            List<UserSubscription> subscriptions = userSubscriptionService.getSubscriptionsByUser(userId);
            return Response.newSuccess(subscriptions);
        } catch (Exception e) {
            return Response.newFail("Failed to fetch subscriptions: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/subscribers")
    public Response<List<UserSubscription>> getUserSubscribers(@PathVariable Long userId) {
        try {
            List<UserSubscription> subscribers = userSubscriptionService.getSubscribersOfUser(userId);
            return Response.newSuccess(subscribers);
        } catch (Exception e) {
            return Response.newFail("Failed to fetch subscribers: " + e.getMessage());
        }
    }
}
