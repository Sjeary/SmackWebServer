package org.example.smackwebserver.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dynamic")
public class Dynamic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // 动态ID

    @Column(name = "user_id", nullable = false)
    private long userId; // 发布者的用户ID

    @Column(name = "title", nullable = false, length = 100)
    private String title; // 动态标题

    @Column(name = "content", nullable = false)
    private String content; // 动态内容

    @Column(name = "url_id")
    private String urlId; // 动态对应产品id

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag_dynamic",
            joinColumns = @JoinColumn(name = "dynamic_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @JsonIgnoreProperties("dynamics")
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
