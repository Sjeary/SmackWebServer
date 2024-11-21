package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.UserSubscription;

import java.util.List;

public interface UserSubscriptionService {

    UserSubscription addSubscription(Long userIdFrom, Long userIdTo);

    void removeSubscription(Long userIdFrom, Long userIdTo);

    List<UserSubscription> getSubscriptionsByUser(Long userId);

    List<UserSubscription> getSubscribersOfUser(Long userId);
}
