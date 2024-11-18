package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.Comment;
import org.example.smackwebserver.dao.CommentRepository;

import java.util.List;

public interface CommentService {
    public List<? extends Comment> getNestedComments(String commentType, int parentId);
}
