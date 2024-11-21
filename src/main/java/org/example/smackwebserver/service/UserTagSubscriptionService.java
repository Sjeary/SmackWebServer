package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.UserTagSubscription;

import java.util.List;

public interface UserTagSubscriptionService {

    UserTagSubscription addSubscription(Long userId, Long tagId);

    void removeSubscription(Long userId, Long tagId);

    List<UserTagSubscription> getSubscriptionsByUser(Long userId);

    List<UserTagSubscription> getUsersSubscribedToTag(Long tagId);
}
