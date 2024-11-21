package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.UserTagSubscription;
import org.example.smackwebserver.dao.UserTagSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTagSubscriptionServiceImpl implements UserTagSubscriptionService {

    private final UserTagSubscriptionRepository userTagSubscriptionRepository;

    public UserTagSubscriptionServiceImpl(UserTagSubscriptionRepository userTagSubscriptionRepository) {
        this.userTagSubscriptionRepository = userTagSubscriptionRepository;
    }

    @Override
    public UserTagSubscription addSubscription(Long userId, Long tagId) {
        if (userTagSubscriptionRepository.findByUserIdAndTagId(userId, tagId).isPresent()) {
            throw new IllegalArgumentException("Already subscribed to this tag.");
        }
        UserTagSubscription subscription = new UserTagSubscription();
        subscription.setUserId(userId);
        subscription.setTagId(tagId);
        return userTagSubscriptionRepository.save(subscription);
    }

    @Override
    public void removeSubscription(Long userId, Long tagId) {
        UserTagSubscription subscription = userTagSubscriptionRepository.findByUserIdAndTagId(userId, tagId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription does not exist."));
        userTagSubscriptionRepository.delete(subscription);
    }

    @Override
    public List<UserTagSubscription> getSubscriptionsByUser(Long userId) {
        return userTagSubscriptionRepository.findByUserId(userId);
    }

    @Override
    public List<UserTagSubscription> getUsersSubscribedToTag(Long tagId) {
        return userTagSubscriptionRepository.findByTagId(tagId);
    }
}
