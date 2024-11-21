package org.example.smackwebserver.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // 评论ID

    @Column(name = "user_id", nullable = false)
    private long userId; // 评论的用户ID

    @Column(name = "reply_id", nullable = true)
    private Integer replyId; // 回复的顶楼Id

    @Column(name = "content", nullable = false)
    private String content; // 评论内容

    @Column(name = "issued_at", nullable = false, updatable = false)
    private LocalDateTime issuedAt; // 发布时间

    @Column(nullable = false)
    private Integer parentId; // 评论对应原帖Id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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
