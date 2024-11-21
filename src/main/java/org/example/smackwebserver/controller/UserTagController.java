package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.UserTagSubscription;
import org.example.smackwebserver.service.UserTagSubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/UserSubTag")
public class UserTagController {

    private final UserTagSubscriptionService userTagSubscriptionService;

    public UserTagController(UserTagSubscriptionService userTagSubscriptionService) {
        this.userTagSubscriptionService = userTagSubscriptionService;
    }

    @PostMapping("/{userId}/{tagId}")
    public Response<UserTagSubscription> addSubscription(@PathVariable Long userId, @PathVariable Long tagId) {
        try {
            UserTagSubscription subscription = userTagSubscriptionService.addSubscription(userId, tagId);
            return Response.newSuccess(subscription);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/{tagId}")
    public Response<String> removeSubscription(@PathVariable Long userId, @PathVariable Long tagId) {
        try {
            userTagSubscriptionService.removeSubscription(userId, tagId);
            return Response.newSuccess("Subscription removed successfully.");
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }

    @GetMapping("/{userId}/subscriptions")
    public Response<List<UserTagSubscription>> getUserSubscriptions(@PathVariable Long userId) {
        try {
            List<UserTagSubscription> subscriptions = userTagSubscriptionService.getSubscriptionsByUser(userId);
            return Response.newSuccess(subscriptions);
        } catch (Exception e) {
            return Response.newFail("Failed to fetch subscriptions: " + e.getMessage());
        }
    }

    @GetMapping("/{tagId}/subscribers")
    public Response<List<UserTagSubscription>> getUsersSubscribedToTag(@PathVariable Long tagId) {
        try {
            List<UserTagSubscription> subscribers = userTagSubscriptionService.getUsersSubscribedToTag(tagId);
            return Response.newSuccess(subscribers);
        } catch (Exception e) {
            return Response.newFail("Failed to fetch subscribers: " + e.getMessage());
        }
    }
}
