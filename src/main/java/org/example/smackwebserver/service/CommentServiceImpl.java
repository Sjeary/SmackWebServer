package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl<T extends Comment> implements CommentService<T> {

    private final CommentRepository<T> commentRepository;

    public CommentServiceImpl(CommentRepository<T> commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 获取带回复的评论（只有一层楼中楼，不然嵌套太深了）
    @Override
    public List<T> getNestedComments(int parentId, Class<T> type) {
        List<T> topLevelComments = commentRepository.findTopByParentId(parentId, type);
        for (T comment : topLevelComments) {
            comment.setReplies(commentRepository.findByReplyId(comment.getId(), type));
        }
        return topLevelComments;
    }

    @Override
    public T createComment(int parentId, T comment, Class<T> type) {
        // 评论内容
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }
        if (comment.getContent().length() > 255) {
            throw new IllegalArgumentException("Comment content length cannot exceed 255 characters");
        }

        // 回复的评论
        if (comment.getReplyId() != null) {
            T f_comment = commentRepository.findById(comment.getReplyId(), type);
            if (f_comment == null) {
                throw new IllegalArgumentException("Original comment not found");
            } else if (f_comment.getReplyId() != null) {
                throw new IllegalArgumentException("Original comment is not top-floor");
            }
        }

        // 设置非空字段
        comment.setParentId(parentId);
        comment.setIssuedAt(LocalDateTime.now());

        return commentRepository.save(comment, type);
    }

    @Override
    public void deleteComments(int parentId, Class<T> type) {
        commentRepository.deleteByParentId(parentId, type);
    }

}
