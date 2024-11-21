package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.UserSubscription;
import org.example.smackwebserver.service.UserSubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/UserSubUser")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @PostMapping("/{userIdFrom}/{userIdTo}")
    public Response<UserSubscription> addSubscription(@PathVariable Long userIdFrom, @PathVariable Long userIdTo) {
        try {
            UserSubscription subscription = userSubscriptionService.addSubscription(userIdFrom, userIdTo);
            return Response.newSuccess(subscription);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }

    @DeleteMapping("/{userIdFrom}/{userIdTo}")
    public Response<String> removeSubscription(@PathVariable Long userIdFrom, @PathVariable Long userIdTo) {
        try {
            userSubscriptionService.removeSubscription(userIdFrom, userIdTo);
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
