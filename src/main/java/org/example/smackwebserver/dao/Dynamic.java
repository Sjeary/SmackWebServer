package org.example.smackwebserver.dao;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dynamics")
public class Dynamic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // 动态ID

    @Column(name = "user_id", nullable = false)
    private int userId; // 发布者的用户ID

    @Column(name = "title", nullable = false, length = 100)
    private String title; // 动态标题

    @Column(name = "type", nullable = false)
    private int type; // 动态类型（0=产品，1=通知）

    @Column(name = "post_id", nullable = false)
    private String postId; // 动态对应原帖id

    @Column(name = "issued_at", nullable = false, updatable = false)
    private LocalDateTime issuedAt; // 发布时间

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
