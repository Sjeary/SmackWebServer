package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl<T extends Comment> implements CommentService<T> {

    private final CommentRepository<T> commentRepository;

    public CommentServiceImpl(CommentRepository<T> commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<T> getNestedComments(int parentId, Class<T> type) {
        List<T> topLevelComments = commentRepository.findTopByParentId(parentId, type);
        for (T comment : topLevelComments) {
            comment.setReplies(commentRepository.findByReplyId(comment.getId(), type));
        }
        return topLevelComments;
    }
    
}
