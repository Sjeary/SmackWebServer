package org.example.smackwebserver.dao;

import java.util.List;

// 自定义动态指定实体类名的查询接口
public interface CustomCommentRepository<T extends Comment> {
    List<T> findByReplyId(Integer replyId, Class<T> type);

    List<T> findTopByParentId(int parentId, Class<T> type);
}

