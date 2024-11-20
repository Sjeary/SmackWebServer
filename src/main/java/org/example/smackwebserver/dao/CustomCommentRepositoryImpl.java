package org.example.smackwebserver.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
        String query = String.format("SELECT c FROM %s c WHERE c.parentId = :parentId AND c.replyId IS null", type.getSimpleName());

        return entityManager.createQuery(query, type)
                .setParameter("parentId", parentId)
                .getResultList();

//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
//        Root<T> root = criteriaQuery.from(type);
//
//        Predicate parentPredicate = criteriaBuilder.equal(root.get("parentId"), parentId);
//        criteriaQuery.select(root).where(parentPredicate);
//
//        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}

