package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.UserSubscription;
import org.example.smackwebserver.dao.UserSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;

    public UserSubscriptionServiceImpl(UserSubscriptionRepository userSubscriptionRepository) {
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public UserSubscription addSubscription(Long userIdFrom, Long userIdTo) {
        if (userIdFrom.equals(userIdTo)) {
            throw new IllegalArgumentException("Cannot subscribe to yourself.");
        }
        if (userSubscriptionRepository.findByUserIdFromAndUserIdTo(userIdFrom, userIdTo).isPresent()) {
            throw new IllegalArgumentException("Already subscribed.");
        }
        UserSubscription subscription = new UserSubscription();
        subscription.setUserIdFrom(userIdFrom);
        subscription.setUserIdTo(userIdTo);
        return userSubscriptionRepository.save(subscription);
    }

    @Override
    public void removeSubscription(Long userIdFrom, Long userIdTo) {
        UserSubscription subscription = userSubscriptionRepository.findByUserIdFromAndUserIdTo(userIdFrom, userIdTo)
                .orElseThrow(() -> new IllegalArgumentException("Subscription does not exist."));
        userSubscriptionRepository.delete(subscription);
    }

    @Override
    public List<UserSubscription> getSubscriptionsByUser(Long userId) {
        return userSubscriptionRepository.findByUserIdFrom(userId);
    }

    @Override
    public List<UserSubscription> getSubscribersOfUser(Long userId) {
        return userSubscriptionRepository.findByUserIdTo(userId);
    }
}
