# 用户关注 Tag 的 接口

---

### 数据库实体代码

定义一个实体类 `UserTagSubscription`：

```java
package org.example.smackwebserver.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "user_tag_subscription")
public class UserTagSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}

```

---

### Repository 接口代码

创建 `UserTagSubscriptionRepository` 用于数据库操作：

```java
package org.example.smackwebserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagSubscriptionRepository extends JpaRepository<UserTagSubscription, Long> {

    Optional<UserTagSubscription> findByUserIdAndTagId(Long userId, Long tagId);

    List<UserTagSubscription> findByUserId(Long userId);

    List<UserTagSubscription> findByTagId(Long tagId);
}

```

---

### 服务层代码

创建 `UserTagSubscriptionService` 和其实现 `UserTagSubscriptionServiceImpl`：

### 接口定义

```java
package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.UserTagSubscription;

import java.util.List;

public interface UserTagSubscriptionService {

    UserTagSubscription addSubscription(Long userId, Long tagId);

    void removeSubscription(Long userId, Long tagId);

    List<UserTagSubscription> getSubscriptionsByUser(Long userId);

    List<UserTagSubscription> getUsersSubscribedToTag(Long tagId);
}

```

### 服务实现

```java
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

```

---

### Controller 层代码

在 `UserTagController` 中实现 `UserTagSubscription` 的增删改查接口：

```java
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

```

---

### 数据库表结构

`user_tag_subscription` 数据库表结构如下：

```sql
CREATE TABLE user_tag_subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    UNIQUE(user_id, tag_id)
);

```

---

### 测试示例

### 添加关注

- **请求**:
    
    ```
    POST /api/v1/UserSubTag/1/2
    
    ```
    
- **响应**:
    
    ```json
    {
        "data": {
            "id": 1,
            "userId": 1,
            "tagId": 2
        },
        "errorMsg": null,
        "success": true
    }
    
    ```
    

### 删除关注

- **请求**:
    
    ```
    DELETE /api/v1/UserSubTag/1/2
    
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
    GET /api/v1/UserSubTag/1/subscriptions
    
    ```
    
- **响应**:
    
    ```json
    {
        "data": [
            {
                "id": 1,
                "userId": 1,
                "tagId": 2
            }
        ],
        "errorMsg": null,
        "success": true
    }
    
    ```
    

### 获取订阅某标签的用户列表

- **请求**:
    
    ```
    GET /api/v1/UserSubTag/2/subscribers
    
    ```
    
- **响应**:
    
    ```json
    {
        "data": [
            {
                "id": 1,
                "userId": 1,
                "tagId": 2
            }
        ],
        "errorMsg": null,
        "success": true
    }
    
    ```
    

这个实现完整支持用户对 `Tag` 的增删改查功能，并且以 RESTful 风格 API 提供接口，满足功能需求。