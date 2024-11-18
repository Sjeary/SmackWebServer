package org.example.smackwebserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository<T extends Comment> extends JpaRepository<T, Integer> {
    List<T> findByParentId(int parentId);

    void deleteByParentId(int parentId);

    List<T> findByReplyId(Integer replyId);

    void deleteByReplyId(Integer replyId);

    @Query("SELECT c FROM #{#entityName} c WHERE c.parentId = :parentId AND c.replyId IS NULL")
    List<T> findTopByParentId(@Param("parentId") int parentId);
}

