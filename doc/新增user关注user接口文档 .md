# 新增user关注user接口文档

下面是实现用户关注功能（增删改查）的相关代码，包括 Controller、Service、Repository 和数据库实体代码。

### 数据库实体代码

首先，定义用户关注的实体 `UserSubscription`：

```java
package org.example.smackwebserver.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "user_subscription")
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id_from", nullable = false)
    private Long userIdFrom;

    @Column(name = "user_id_to", nullable = false)
    private Long userIdTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(Long userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public Long getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(Long userIdTo) {
        this.userIdTo = userIdTo;
    }
}

```

---

### Repository 接口代码

创建 `UserSubscriptionRepository` 用于数据库操作：

```java
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

```

---

### 服务层代码

创建 `UserSubscriptionService` 和其实现 `UserSubscriptionServiceImpl`：

### 接口定义

```java
package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.UserSubscription;

import java.util.List;

public interface UserSubscriptionService {

    UserSubscription addSubscription(Long userIdFrom, Long userIdTo);

    void removeSubscription(Long userIdFrom, Long userIdTo);

    List<UserSubscription> getSubscriptionsByUser(Long userId);

    List<UserSubscription> getSubscribersOfUser(Long userId);
}

```

### 服务实现

```java
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

```

---

### Controller 层代码

在 `UserController` 中实现 `UserSubscription` 的增删改查接口：

```java
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

```

---

### 数据库表结构

对应的 `user_subscription` 数据库表结构如下：

```sql
CREATE TABLE user_subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id_from BIGINT NOT NULL,
    user_id_to BIGINT NOT NULL,
    UNIQUE(user_id_from, user_id_to)
);

```

---

### 测试示例

### 添加关注

- **请求**:
    
    ```
    POST /api/v1/UserSubUser/1/2
    
    ```
    
- **响应**:
    
    ```json
    {
        "data": {
            "id": 1,
            "userIdFrom": 1,
            "userIdTo": 2
        },
        "errorMsg": null,
        "success": true
    }
    
    ```
    

### 删除关注

- **请求**:
    
    ```
    DELETE /api/v1/UserSubUser/1/2
    
    ```
    
- **响应**:
    
    ```json
    {
        "data": "Subscription removed successfully.",
        "errorMsg": null,
        "success": true
    }
    
    ```
    

### 获取用户的关注列表

- **请求**:
    
    ```
    GET /api/v1/UserSubUser/1/subscriptions
    
    ```
    
- **响应**:
    
    ```json
    {
        "data": [
            {
                "id": 1,
                "userIdFrom": 1,
                "userIdTo": 2
            }
        ],
        "errorMsg": null,
        "success": true
    }
    
    ```
    

### 获取用户的粉丝列表

- **请求**:
    
    ```
    GET /api/v1/UserSubUser/2/subscribers
    
    ```
    
- **响应**:
    
    ```json
    {
        "data": [
            {
                "id": 1,
                "userIdFrom": 1,
                "userIdTo": 2
            }
        ],
        "errorMsg": null,
        "success": true
    }
    
    ```