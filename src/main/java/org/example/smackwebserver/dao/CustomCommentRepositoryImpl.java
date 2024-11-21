package org.example.smackwebserver.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

// 自定义动态指定实体类名的查询接口
@Repository
public class CustomCommentRepositoryImpl<T extends Comment> implements CustomCommentRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(CustomCommentRepositoryImpl.class);

    @Override
    public List<T> findByReplyId(Integer replyId, Class<T> type){
        String query = String.format("SELECT c FROM %s c WHERE c.replyId = :replyId", type.getSimpleName());

        return entityManager.createQuery(query, type)
                .setParameter("replyId", replyId)
                .getResultList();
    }

    @Override
    public List<T> findTopByParentId(int parentId, Class<T> type) {
        String query = String.format("SELECT c FROM %s c WHERE c.parentId = :parentId AND c.replyId IS NULL", type.getSimpleName());

        return entityManager.createQuery(query, type)
                .setParameter("parentId", parentId)
                .getResultList();
    }

    @Override
    public T findById(int id, Class<T> type) {
        String query = String.format("SELECT c FROM %s c WHERE c.id = :id", type.getSimpleName());

        return entityManager.createQuery(query, type)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public T save(T comment, Class<T> type) {
        entityManager.persist(comment);  // 持久化评论
        return comment;
    }

    @Transactional
    @Override
    public void deleteByParentId(int parentId, Class<T> type) {
        String query = String.format("DELETE FROM %s c WHERE c.parentId = :parentId", type.getSimpleName());
        entityManager.createQuery(query)
                .setParameter("parentId", parentId)
                .executeUpdate();
    }
}

