package org.example.smackwebserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface CommentRepository<T extends Comment> extends JpaRepository<T, Integer>, CustomCommentRepository<T> {

//    @Query("SELECT c FROM #{#entityName} c WHERE c.parentId = :parentId AND c.replyId IS NULL")
//    List<T> findTopByParentId(@Param("parentId") int parentId);
}

