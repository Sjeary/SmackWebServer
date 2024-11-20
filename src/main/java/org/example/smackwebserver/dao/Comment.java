package org.example.smackwebserver.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // 评论ID

    @Column(name = "user_id", nullable = false)
    private int userId; // 评论的用户ID

    @Column(name = "reply_id", nullable = false, length = 100)
    private Integer replyId; // 回复的顶楼Id

    @Column(name = "content", nullable = false)
    private String content; // 评论内容

    @Column(name = "issued_at", nullable = false, updatable = false)
    private LocalDateTime issuedAt; // 发布时间

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 更新时间

    @Column(nullable = false)
    private int parentId; // 评论对应原帖Id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Transient
    private List<? extends Comment> replies;

    public List<? extends Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<? extends Comment> replies) {
        this.replies = replies;
    }
}
