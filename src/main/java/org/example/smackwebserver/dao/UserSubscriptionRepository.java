package org.example.smackwebserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    Optional<UserSubscription> findByUserIdFromAndUserIdTo(Long userIdFrom, Long userIdTo);

    List<UserSubscription> findByUserIdFrom(Long userIdFrom);

    List<UserSubscription> findByUserIdTo(Long userIdTo);
}
