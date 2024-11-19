package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.Comment;
import org.example.smackwebserver.dao.CommentRepository;

import java.util.List;

public interface CommentService<T extends Comment> {
    public List<T> getNestedComments(int parentId, Class<T> type); // 获取带回复嵌套的评论
}
